package com.example.patientapp.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("st_lukas_prefs", Context.MODE_PRIVATE)

    companion object {
        const val KEY_USER_ID = "USER_ID"
        const val KEY_USERNAME = "USERNAME"
        const val KEY_ROLE = "ROLE"
    }

    fun saveSession(userId: Int, username: String, role: String) {
        prefs.edit().apply {
            putInt(KEY_USER_ID, userId)
            putString(KEY_USERNAME, username)
            putString(KEY_ROLE, role)
            apply()
        }
    }

    fun getUserId(): Int = prefs.getInt(KEY_USER_ID, -1)
    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)
    fun getRole(): String? = prefs.getString(KEY_ROLE, null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
    
    fun isLoggedIn(): Boolean {
        return getUserId() != -1
    }
}
