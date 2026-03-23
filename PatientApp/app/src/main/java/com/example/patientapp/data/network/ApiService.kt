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


interface PatientApiService {
    @GET("/api/patient/user/{id}")
    suspend fun getPatient(@Path("id") id: Long): Response<com.example.patientapp.data.model.Patient>

    @PUT("/api/patient")
    suspend fun registerPatient(@Body patient: com.example.patientapp.data.model.Patient): Response<com.example.patientapp.data.model.Patient>
}
