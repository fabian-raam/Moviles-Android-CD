package com.ramirez.citas

sealed class Screen(val route: String) {
    object Home : Screen("home")

    object DoctorDetail : Screen("doctor_detail/{doctorName}") {
        fun createRoute(doctorName: String): String {
            return "doctor_detail/$doctorName"
        }
    }

    object Appointment : Screen("appointment")
    object Confirmation : Screen("confirmation")
    object Appointments : Screen("appointments")
    object MedicalHistory : Screen("medical_history")
    object Profile : Screen("profile")
}