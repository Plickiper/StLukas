package com.example.patientapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Doctor(
    val id: Long = 0,
    val userId: Long? = null,
    val username: String,
    val password: String? = null,
    val role: String = "DOCTOR",
    val firstName: String,
    val lastName: String,
    val specialization: String,
    val availabilityStatus: String = "AVAILABLE"
) : Parcelable
