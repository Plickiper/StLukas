package com.example.patientapp.ui.doctor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patientapp.data.model.Appointment
import com.example.patientapp.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DoctorViewModel : ViewModel() {
    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _doctorProfile = MutableStateFlow<com.example.patientapp.data.model.Doctor?>(null)
    val doctorProfile: StateFlow<com.example.patientapp.data.model.Doctor?> = _doctorProfile

    fun fetchDoctorAppointments(userId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. Resolve Doctor Profile (and its real ID) from User ID if not already cached
                if (_doctorProfile.value == null) {
                    val doctorResponse = RetrofitClient.doctorApi.getAllDoctors()
                    if (doctorResponse.isSuccessful && doctorResponse.body() != null) {
                        val profile = doctorResponse.body()!!.find { it.userId == userId }
                        _doctorProfile.value = profile
                    }
                }

                val realDoctorId = _doctorProfile.value?.id ?: userId // Fallback to userId if profile not found

                // 2. Fetch from Admin microservice using the realDoctorId
                val response = RetrofitClient.appointmentApi.getAllAppointments()
                if (response.isSuccessful && response.body() != null) {
                    _appointments.value = response.body()!!.filter { it.doctorId == realDoctorId }
                } else {
                    _errorMessage.value = "Failed to load appointments"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateAppointmentStatus(appointment: Appointment, newStatus: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val updatedAppointment = appointment.copy(
                    status = newStatus,
                    role = "DOCTOR"
                )
                val response = RetrofitClient.appointmentApi.updateAppointment(updatedAppointment)
                if (response.isSuccessful) {
                    fetchDoctorAppointments(appointment.doctorId!!)
                    onSuccess()
                } else {
                    _errorMessage.value = "Failed to update status"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleAvailability(onSuccess: () -> Unit) {
        val profile = _doctorProfile.value ?: return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val newStatus = if (profile.availabilityStatus == "AVAILABLE") "NOT_AVAILABLE" else "AVAILABLE"
                val updatedProfile = profile.copy(availabilityStatus = newStatus)
                
                val response = RetrofitClient.doctorApi.updateDoctorProfile(updatedProfile)
                if (response.isSuccessful) {
                    _doctorProfile.value = response.body()
                    onSuccess()
                } else {
                    _errorMessage.value = "Failed to update availability"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createFollowUp(parentAppointment: Appointment, followUpDate: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val followUp = Appointment(
                    patientId = parentAppointment.patientId,
                    doctorId = parentAppointment.doctorId,
                    preferredDate = followUpDate,
                    reason = "Follow up from appointment #${parentAppointment.id}",
                    status = "FOLLOW_UP_PENDING",
                    role = "DOCTOR",
                    parentAppointmentId = parentAppointment.id.toLong()
                )
                val response = RetrofitClient.appointmentApi.requestAppointment(followUp)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    _errorMessage.value = "Failed to schedule follow up"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
