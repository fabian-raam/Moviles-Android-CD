package com.ramirez.tecfit

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ClassDetail : Screen("class_detail/{className}") {
        fun createRoute(className: String): String = "class_detail/$className"
    }
    object Confirmation : Screen("confirmation/{className}/{schedule}/{room}") {
        fun createRoute(className: String, schedule: String, room: String): String =
            "confirmation/$className/$schedule/$room"
    }
    object Reservations : Screen("reservations")
    object Routines : Screen("routines")
    object Profile : Screen("profile")
}
