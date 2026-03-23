package com.example.patientapp.ui.patient

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAppointmentScreen(
    patientId: Long,
    patientViewModel: PatientViewModel,
    onNavigateBack: () -> Unit
) {
    var preferredDate by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    
    val isLoading by patientViewModel.isLoading.collectAsState()
    val errorMessage by patientViewModel.errorMessage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Request Appointment") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = preferredDate,
                onValueChange = { preferredDate = it },
                label = { Text("Preferred Date (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                label = { Text("Reason for Visit") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Button(
                onClick = {
                    patientViewModel.requestAppointment(
                        patientId = patientId,
                        preferredDate = preferredDate,
                        reason = reason,
                        onSuccess = onNavigateBack
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && preferredDate.isNotBlank() && reason.isNotBlank()
            ) {
                Text(if (isLoading) "Submitting..." else "Submit Request")
            }
            
            if (errorMessage != null) {
                Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
