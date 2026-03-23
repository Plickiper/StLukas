package com.example.patientapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.patientapp.ui.navigation.AppNavigation
import com.example.patientapp.ui.theme.PatientAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PatientAppTheme {
                AppNavigation()
            }
        }
    }
}