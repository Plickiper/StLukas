package com.example.patientapp.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patientapp.data.model.Appointment
import com.example.patientapp.data.model.Doctor
import com.example.patientapp.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    private val _doctors = MutableStateFlow<List<Doctor>>(emptyList())
    val doctors: StateFlow<List<Doctor>> = _doctors



    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchPendingAppointments() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.appointmentApi.getAllAppointments()
                if (response.isSuccessful && response.body() != null) {
                    _appointments.value = response.body()!!.filter { 
                        it.status == "REQUESTED" || it.status == "FOLLOW_UP_PENDING" 
                    }
                } else {
                    _errorMessage.value = "Failed to load requests"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchDoctors() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.doctorApi.getAllDoctors()
                if (response.isSuccessful && response.body() != null) {
                    _doctors.value = response.body()!!.filter { it.availabilityStatus == "AVAILABLE" }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error loading doctors: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }



    fun createDoctor(
        username: String,
        password: String,
        firstName: String,
        lastName: String,
        specialization: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val doctor = Doctor(
                    id = 0,
                    username = username,
                    password = password,
                    firstName = firstName,
                    lastName = lastName,
                    specialization = specialization,
                    availabilityStatus = "AVAILABLE"
                )
                val response = RetrofitClient.doctorApi.createDoctor(doctor)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    _errorMessage.value = "Failed to create doctor: ${response.errorBody()?.string()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun assignDoctor(
        appointment: Appointment,
        doctorId: Long,
        scheduledDate: String,
        scheduledTime: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val updatedAppointment = appointment.copy(
                    doctorId = doctorId,
                    scheduledDate = scheduledDate,
                    scheduledTime = scheduledTime,
                    status = "PENDING_DOCTOR_CONFIRMATION",
                    role = "ADMIN"
                )
                
                val response = RetrofitClient.appointmentApi.updateAppointment(updatedAppointment)
                if (response.isSuccessful) {
                    fetchPendingAppointments()
                    onSuccess()
                } else {
                    _errorMessage.value = "Failed to assign doctor"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
