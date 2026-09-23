package com.ramirez.tecfit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            Text(text = "Pantalla de Inicio")
        }
        composable(
            route = Screen.ClassDetail.route,
            arguments = listOf(navArgument("className") { type = NavType.StringType })
        ) { backStackEntry ->
            val className = backStackEntry.arguments?.getString("className") ?: ""
            Text(text = "Detalle de: $className")
        }
        composable(Screen.Confirmation.route) {
            Text(text = "Confirmación")
        }
        composable(Screen.Reservations.route) {
            Text(text = "Mis reservas")
        }
        composable(Screen.Routines.route) {
            Text(text = "Rutinas")
        }
        composable(Screen.Profile.route) {
            Text(text = "Perfil")
        }
    }
}
