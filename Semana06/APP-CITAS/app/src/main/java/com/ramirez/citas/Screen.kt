package com.ramirez.citas

sealed class Screen(val route: String) {

    object Home : Screen("home")

    object DoctorDetail : Screen("doctor_detail/{doctorName}") {
        fun createRoute(doctorName: String): String {
            return "doctor_detail/$doctorName"
        }
    }

    object Appointment : Screen("appointment/{doctorName}") {
        fun createRoute(doctorName: String): String {
            return "appointment/$doctorName"
        }
    }

    object Confirmation :
        Screen("confirmation/{doctorName}/{date}/{time}") {

        fun createRoute(
            doctorName: String,
            date: String,
            time: String
        ): String {
            return "confirmation/$doctorName/$date/$time"
        }
    }

    object Appointments : Screen("appointments")
    object MedicalHistory : Screen("medical_history")
    object Profile : Screen("profile")
}