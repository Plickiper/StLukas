package com.example.patientapp.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL_PATIENT = "http://10.0.2.2:8081"
    private const val BASE_URL_DOCTOR = "http://10.0.2.2:8082"
    private const val BASE_URL_ADMIN = "http://10.0.2.2:8083"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Auth flows hit different ports depending on the user.
    // By default, let's say the Patient app is logging in patients -> 8081
    val patientAuthApi: AuthApiService by lazy {
        getRetrofit(BASE_URL_PATIENT).create(AuthApiService::class.java)
    }

    val appointmentApi: AppointmentApiService by lazy {
        getRetrofit(BASE_URL_ADMIN).create(AppointmentApiService::class.java)
    }

    val patientApi: PatientApiService by lazy {
        getRetrofit(BASE_URL_PATIENT).create(PatientApiService::class.java)
    }
}
