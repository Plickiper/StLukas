package com.example.patientapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.patientapp.ui.admin.AdminDashboardScreen
import com.example.patientapp.ui.admin.AdminViewModel
import com.example.patientapp.ui.admin.AssignDoctorScreen
import com.example.patientapp.ui.admin.CreateDoctorScreen
import com.example.patientapp.ui.auth.AuthViewModel
import com.example.patientapp.ui.auth.LoginScreen
import com.example.patientapp.ui.doctor.DoctorDashboardScreen
import com.example.patientapp.ui.doctor.DoctorViewModel
import com.example.patientapp.util.SessionManager

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sessionManager = SessionManager(context)

    // ViewModels scoped to the navigation graph
    val authViewModel: AuthViewModel = viewModel()
    val adminViewModel: AdminViewModel = viewModel()
    val doctorViewModel: DoctorViewModel = viewModel()

    val startDestination = if (sessionManager.isLoggedIn()) {
        when (sessionManager.getRole()) {
            "ADMIN" -> "admin_dashboard"
            "DOCTOR" -> "doctor_dashboard"
            else -> "login" // Shouldn't happen
        }
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
                    val route = when (user.role) {
                        "ADMIN" -> "admin_dashboard"
                        "DOCTOR" -> "doctor_dashboard"
                        else -> "login" // Shouldn't happen
                    }
                    navController.navigate(route) { popUpTo(0) }
                },
                onResetState = { authViewModel.resetState() }
            )
        }

        // NO PATIENT ROUTES

        // ADMIN
        composable("admin_dashboard") {
            AdminDashboardScreen(
                adminViewModel = adminViewModel,
                onNavigateToAssign = { appointment -> 
                    navController.currentBackStackEntry?.savedStateHandle?.set("selected_appointment", appointment)
                    navController.navigate("assign_doctor")
                },
                onNavigateToCreateDoctor = {
                    navController.navigate("create_doctor")
                },
                onLogout = {
                    sessionManager.clearSession()
                    navController.navigate("login") { popUpTo(0) }
                }
            )
        }

        composable("create_doctor") {
            CreateDoctorScreen(
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("assign_doctor") {
            val appointment = navController.previousBackStackEntry?.savedStateHandle?.get<com.example.patientapp.data.model.Appointment>("selected_appointment")
            if (appointment != null) {
                AssignDoctorScreen(
                    appointment = appointment,
                    adminViewModel = adminViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }

        // DOCTOR
        composable("doctor_dashboard") {
            DoctorDashboardScreen(
                doctorId = sessionManager.getUserId().toLong(),
                doctorViewModel = doctorViewModel,
                onLogout = {
                    sessionManager.clearSession()
                    navController.navigate("login") { popUpTo(0) }
                }
            )
        }
    }
}
