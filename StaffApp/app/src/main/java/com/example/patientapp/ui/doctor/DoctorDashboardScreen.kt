package com.example.patientapp.ui.doctor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.patientapp.data.model.Appointment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDashboardScreen(
    doctorId: Long,
    doctorViewModel: DoctorViewModel,
    onLogout: () -> Unit
) {
    val appointments by doctorViewModel.appointments.collectAsState()
    val doctorProfile by doctorViewModel.doctorProfile.collectAsState()
    val isLoading by doctorViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        doctorViewModel.fetchDoctorAppointments(doctorId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(doctorProfile?.let { "Dr. ${it.firstName} ${it.lastName}" } ?: "Doctor Dashboard") 
                },
                actions = {
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        Text(
                            text = if (doctorProfile?.availabilityStatus == "AVAILABLE") "Available" else "Busy",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (doctorProfile?.availabilityStatus == "AVAILABLE") 
                                androidx.compose.ui.graphics.Color(0xFF4CAF50) 
                            else 
                                androidx.compose.ui.graphics.Color(0xFFF44336)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Switch(
                            checked = doctorProfile?.availabilityStatus == "AVAILABLE",
                            onCheckedChange = { _ ->
                                doctorViewModel.toggleAvailability { }
                            },
                            enabled = !isLoading && doctorProfile != null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        TextButton(onClick = onLogout) {
                            Text("Logout", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (appointments.isEmpty() && !isLoading) {
                    item {
                        Text("No appointments assigned.", modifier = Modifier.padding(16.dp))
                    }
                }
                items(appointments) { appointment ->
                    DoctorAppointmentCard(
                        appointment = appointment,
                        onUpdateStatus = { status ->
                            doctorViewModel.updateAppointmentStatus(appointment, status) {}
                        },
                        onCreateFollowUp = { date ->
                            doctorViewModel.createFollowUp(appointment, date) {
                                // optional feedback
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DoctorAppointmentCard(
    appointment: Appointment,
    onUpdateStatus: (String) -> Unit,
    onCreateFollowUp: (String) -> Unit
) {
    var followUpDate by remember { mutableStateOf("") }
    var showFollowUpField by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Patient ID: ${appointment.patientId}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Status: ${appointment.status}", color = MaterialTheme.colorScheme.primary)
            Text(text = "Date: ${appointment.scheduledDate} ${appointment.scheduledTime ?: ""}")
            Text(text = "Reason: ${appointment.reason}", style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(8.dp))

            when (appointment.status) {
                "PENDING_DOCTOR_CONFIRMATION" -> {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { onUpdateStatus("CONFIRMED") }) { Text("Accept") }
                        OutlinedButton(onClick = { onUpdateStatus("REJECTED") }) { Text("Reject") }
                    }
                }
                "CONFIRMED" -> {
                    Button(onClick = { onUpdateStatus("COMPLETED") }) { Text("Mark Completed") }
                }
                "COMPLETED" -> {
                    if (!showFollowUpField) {
                        TextButton(onClick = { showFollowUpField = true }) {
                            Text("Schedule Follow-Up")
                        }
                    } else {
                        OutlinedTextField(
                            value = followUpDate,
                            onValueChange = { followUpDate = it },
                            label = { Text("Follow-up Date (yyyy-MM-dd)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(
                            onClick = { 
                                onCreateFollowUp(followUpDate)
                                showFollowUpField = false
                            },
                            enabled = followUpDate.isNotBlank()
                        ) {
                            Text("Submit Follow-Up")
                        }
                    }
                }
            }
        }
    }
}
