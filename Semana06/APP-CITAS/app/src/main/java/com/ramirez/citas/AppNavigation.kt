package com.ramirez.citas

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onDoctorClick = {
                    navController.navigate(Screen.DoctorDetail.route)
                }
            )
        }
        composable(Screen.DoctorDetail.route) {
            Text(text = "Doctor Detail Screen")
        }
        composable(Screen.Appointment.route) {
            Text(text = "Appointment Screen")
        }
        composable(Screen.Confirmation.route) {
            Text(text = "Confirmation Screen")
        }
        composable(Screen.Appointments.route) {
            Text(text = "Appointments Screen")
        }
        composable(Screen.MedicalHistory.route) {
            Text(text = "Medical History Screen")
        }
        composable(Screen.Profile.route) {
            Text(text = "Profile Screen")
        }
    }
}
