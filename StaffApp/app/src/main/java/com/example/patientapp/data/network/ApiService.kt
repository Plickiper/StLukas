package com.example.patientapp.data.network

import com.example.patientapp.data.model.Appointment
import com.example.patientapp.data.model.User
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthApiService {
    @POST("/api/user")
    suspend fun register(@Body requestBody: RequestBody): Response<User>

    @POST("/api/user/login")
    suspend fun login(@Body requestBody: RequestBody): Response<User>
}

interface AppointmentApiService {
    @GET("/api/appointment")
    suspend fun getAllAppointments(): Response<List<Appointment>>

    @GET("/api/appointment/{id}")
    suspend fun getAppointment(@Path("id") id: Int): Response<Appointment>

    @PUT("/api/appointment")
    suspend fun requestAppointment(@Body appointment: Appointment): Response<Appointment>

    @POST("/api/appointment")
    suspend fun updateAppointment(@Body appointment: Appointment): Response<Appointment>
}

interface DoctorApiService {
    @GET("/api/doctor")
    suspend fun getAllDoctors(): Response<List<com.example.patientapp.data.model.Doctor>>

    @PUT("/api/doctor")
    suspend fun createDoctor(@Body doctor: com.example.patientapp.data.model.Doctor): Response<com.example.patientapp.data.model.Doctor>

    @POST("/api/doctor")
    suspend fun updateDoctorProfile(@Body doctor: com.example.patientapp.data.model.Doctor): Response<com.example.patientapp.data.model.Doctor>
}


