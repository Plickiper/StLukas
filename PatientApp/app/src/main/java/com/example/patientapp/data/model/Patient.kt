package com.example.patientapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patient(
    val id: Int = 0,
    val userId: Long? = null,
    val username: String = "",
    val password: String = "",
    val role: String = "PATIENT",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val dateOfBirth: String = "",
    val age: Int = 0,
    val address: String = "",
    val emergencyContact: String = ""
) : Parcelable
