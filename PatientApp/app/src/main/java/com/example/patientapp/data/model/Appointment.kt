package com.example.patientapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Appointment(
    val id: Int = 0,
    val patientId: Long,
    val doctorId: Long? = null,
    val preferredDate: String, // Format: yyyy-MM-dd
    val preferredTimeRange: String? = null,
    val scheduledDate: String? = null, // Assigned by Admin
    val scheduledTime: String? = null, // Assigned by Admin
    val reason: String,
    val status: String,
    val role: String, // The role of the user triggering the update to pass backend validation.
    val parentAppointmentId: Long? = null, // Only populated if this is a Follow-Up.
    val doctorName: String? = null, // Cached for UI display
    val patientName: String? = null // Cached for UI display
) : Parcelable
