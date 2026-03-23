package com.example.patientapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.patientapp.ui.auth.AuthViewModel
import com.example.patientapp.ui.auth.LoginScreen
import com.example.patientapp.ui.auth.RegisterScreen
import com.example.patientapp.ui.patient.CreateAppointmentScreen
import com.example.patientapp.ui.patient.PatientDashboardScreen
import com.example.patientapp.ui.patient.PatientViewModel
import com.example.patientapp.util.SessionManager

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sessionManager = SessionManager(context)

    // ViewModels scoped to the navigation graph
    val authViewModel: AuthViewModel = viewModel()
    val patientViewModel: PatientViewModel = viewModel()

    val startDestination = if (sessionManager.isLoggedIn()) {
        "patient_dashboard"
    } else {
        "login"
    }

    NavHost(navController = navController, startDestination = startDestination) {
        
        // AUTHENTICATION
        composable("login") {
            LoginScreen(
                authStateFlow = authViewModel.authState,
                onLoginClick = { user, pass, role -> authViewModel.login(user, pass, role) },
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess = { user ->
                    sessionManager.saveSession(user.id, user.username, user.role)
                    navController.navigate("patient_dashboard") { popUpTo(0) }
                },
                onResetState = { authViewModel.resetState() }
            )
        }

        composable("register") {
            RegisterScreen(
                authStateFlow = authViewModel.authState,
                onRegisterClick = { patient -> authViewModel.register(patient) },
                onNavigateToLogin = { navController.navigate("login") },
                onRegisterSuccess = {
                    navController.navigate("login") { popUpTo("login") { inclusive = true } }
                },
                onResetState = { authViewModel.resetState() }
            )
        }

        // PATIENT
        composable("patient_dashboard") {
            PatientDashboardScreen(
                patientId = sessionManager.getUserId().toLong(),
                patientViewModel = patientViewModel,
                onNavigateToCreate = { navController.navigate("create_appointment") },
                onLogout = {
                    sessionManager.clearSession()
                    navController.navigate("login") { popUpTo(0) }
                }
            )
        }

        composable("create_appointment") {
            CreateAppointmentScreen(
                patientId = sessionManager.getUserId().toLong(),
                patientViewModel = patientViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // DO NOT contain admin/doctor paths
    }
}
