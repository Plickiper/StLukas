package com.example.patientapp.ui.patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patientapp.data.model.Appointment
import com.example.patientapp.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientViewModel : ViewModel() {
    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchPatientAppointments(patientId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch from Admin microservice (Since Admin handles appointments)
                val response = RetrofitClient.appointmentApi.getAllAppointments()
                if (response.isSuccessful && response.body() != null) {
                    // Filter locally by patientId
                    _appointments.value = response.body()!!.filter { it.patientId == patientId }
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

    fun requestAppointment(patientId: Long, preferredDate: String, reason: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val newAppointment = Appointment(
                    patientId = patientId,
                    preferredDate = preferredDate,
                    reason = reason,
                    status = "REQUESTED",
                    role = "PATIENT" // Pass role for backend validation
                )
                val response = RetrofitClient.appointmentApi.requestAppointment(newAppointment)
                if (response.isSuccessful) {
                    fetchPatientAppointments(patientId)
                    onSuccess()
                } else {
                    _errorMessage.value = "Failed to create appointment request"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
