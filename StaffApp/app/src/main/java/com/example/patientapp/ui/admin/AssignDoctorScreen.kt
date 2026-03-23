package com.example.patientapp.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.patientapp.data.model.Appointment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignDoctorScreen(
    appointment: Appointment,
    adminViewModel: AdminViewModel,
    onNavigateBack: () -> Unit
) {
    val doctors by adminViewModel.doctors.collectAsState()
    val isLoading by adminViewModel.isLoading.collectAsState()

    var scheduledDate by remember { mutableStateOf("") }
    var scheduledTime by remember { mutableStateOf("") }
    
    var expanded by remember { mutableStateOf(false) }
    var selectedDoctorId by remember { mutableStateOf<Long?>(null) }
    var selectedDoctorName by remember { mutableStateOf("Select Doctor") }

    LaunchedEffect(Unit) {
        adminViewModel.fetchDoctors()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assign Doctor") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Request Reason: ${appointment.reason}", style = MaterialTheme.typography.bodyLarge)
            Text("Preferred Date: ${appointment.preferredDate}", style = MaterialTheme.typography.bodyMedium)
            
            // Doctor Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = selectedDoctorName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Assign To Doctor") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    if (doctors.isEmpty() && !isLoading) {
                        DropdownMenuItem(
                            text = { Text("No doctors available") },
                            onClick = { expanded = false }
                        )
                    }
                    doctors.forEach { doc ->
                        DropdownMenuItem(
                            text = { Text("${doc.firstName} ${doc.lastName} (${doc.specialization})") },
                            onClick = {
                                selectedDoctorId = doc.id
                                selectedDoctorName = "${doc.firstName} ${doc.lastName}"
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = scheduledDate,
                onValueChange = { scheduledDate = it },
                label = { Text("Final Scheduled Date (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = scheduledTime,
                onValueChange = { scheduledTime = it },
                label = { Text("Final Scheduled Time (HH:mm)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (selectedDoctorId != null) {
                        adminViewModel.assignDoctor(
                            appointment = appointment,
                            doctorId = selectedDoctorId!!,
                            scheduledDate = scheduledDate,
                            scheduledTime = scheduledTime,
                            onSuccess = onNavigateBack
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && selectedDoctorId != null && scheduledDate.isNotBlank() && scheduledTime.isNotBlank()
            ) {
                Text(if (isLoading) "Assigning..." else "Confirm Assignment")
            }
        }
    }
}
