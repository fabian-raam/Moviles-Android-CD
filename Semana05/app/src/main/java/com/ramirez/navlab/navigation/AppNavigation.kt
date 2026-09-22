package com.ramirez.navlab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ramirez.navlab.screens.DetailScreen
import com.ramirez.navlab.screens.HomeScreen
import com.ramirez.navlab.screens.ListScreen
import com.ramirez.navlab.screens.LoginScreen
import com.ramirez.navlab.screens.ProfileScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.List.route) {
            ListScreen(navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->

            val itemId =
                backStackEntry.arguments?.getInt("itemId") ?: 0

            DetailScreen(
                navController = navController,
                itemId = itemId
            )
        }
    }
}