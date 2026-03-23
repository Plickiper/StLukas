package com.example.patientapp.data.model

data class User(
    val id: Int = 0,
    val username: String,
    val role: String // "PATIENT", "DOCTOR", or "ADMIN"
)
