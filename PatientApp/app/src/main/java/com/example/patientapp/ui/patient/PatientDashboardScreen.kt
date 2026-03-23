package com.example.patientapp.ui.patient

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.patientapp.data.model.Appointment
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDashboardScreen(
    patientId: Long,
    patientViewModel: PatientViewModel,
    onNavigateToCreate: () -> Unit,
    onLogout: () -> Unit
) {
    val appointments by patientViewModel.appointments.collectAsState()
    val isLoading by patientViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        patientViewModel.fetchPatientAppointments(patientId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Appointments") },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Logout", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Request Appointment")
            }
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
                        Text("No appointments found.", modifier = Modifier.padding(16.dp))
                    }
                }
                items(appointments) { appointment ->
                    AppointmentCard(appointment)
                }
            }
        }
    }
}

@Composable
fun AppointmentCard(appointment: Appointment) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Status: ${appointment.status}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Preferred Date: ${appointment.preferredDate}")
            if (appointment.scheduledDate != null) {
                Text(text = "Scheduled: ${appointment.scheduledDate} ${appointment.scheduledTime ?: ""}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Reason: ${appointment.reason}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
