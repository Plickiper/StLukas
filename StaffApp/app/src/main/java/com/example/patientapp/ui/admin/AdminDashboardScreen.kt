package com.example.patientapp.ui.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.patientapp.data.model.Appointment
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    adminViewModel: AdminViewModel,
    onNavigateToAssign: (Appointment) -> Unit,
    onNavigateToCreateDoctor: () -> Unit,
    onLogout: () -> Unit
) {
    val appointments by adminViewModel.appointments.collectAsState()
    val isLoading by adminViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        adminViewModel.fetchPendingAppointments()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard (Requests)") },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Logout", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreateDoctor,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Text("+ Doctor", modifier = Modifier.padding(horizontal = 8.dp))
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
                        Text("No pending requests.", modifier = Modifier.padding(16.dp))
                    }
                }
                items(appointments) { appointment ->
                    AdminRequestCard(
                        appointment = appointment,
                        onClick = { onNavigateToAssign(appointment) }
                    )
                }
            }
        }
    }
}

@Composable
fun AdminRequestCard(appointment: Appointment, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Patient: ${appointment.patientName ?: "ID: ${appointment.patientId}"}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Status: ${appointment.status.replace("_", " ")}", color = MaterialTheme.colorScheme.primary)
            
            appointment.doctorName?.let {
                Text(text = "Assigned to: $it", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
            }
            
            Text(text = "Preferred: ${appointment.preferredDate}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Reason: ${appointment.reason}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Tap to Manage", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
        }
    }
}
