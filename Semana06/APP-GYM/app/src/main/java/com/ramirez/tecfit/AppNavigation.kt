package com.ramirez.tecfit

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem("Inicio", Screen.Home.route, Icons.Default.Home),
        BottomNavItem("Reservas", Screen.Reservations.route, Icons.Default.DateRange),
        BottomNavItem("Rutinas", Screen.Routines.route, Icons.AutoMirrored.Filled.List),
        BottomNavItem("Perfil", Screen.Profile.route, Icons.Default.Person)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (bottomNavItems.any { it.route == currentRoute }) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title) },
                            selected = currentRoute == item.route,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onClassClick = { className ->
                        val encodedName = Uri.encode(className)
                        navController.navigate(Screen.ClassDetail.createRoute(encodedName))
                    }
                )
            }
            composable(
                route = Screen.ClassDetail.route,
                arguments = listOf(navArgument("className") { type = NavType.StringType })
            ) { backStackEntry ->
                val rawName = backStackEntry.arguments?.getString("className") ?: ""
                val className = Uri.decode(rawName)
                ClassDetailScreen(
                    className = className,
                    onBackClick = { navController.popBackStack() },
                    onReserveClick = { name, schedule, room ->
                        val route = Screen.Confirmation.createRoute(
                            className = Uri.encode(name),
                            schedule = Uri.encode(schedule),
                            room = Uri.encode(room)
                        )
                        navController.navigate(route)
                    }
                )
            }
            composable(
                route = Screen.Confirmation.route,
                arguments = listOf(
                    navArgument("className") { type = NavType.StringType },
                    navArgument("schedule") { type = NavType.StringType },
                    navArgument("room") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val rawName = backStackEntry.arguments?.getString("className") ?: ""
                val rawSchedule = backStackEntry.arguments?.getString("schedule") ?: ""
                val rawRoom = backStackEntry.arguments?.getString("room") ?: ""

                val className = Uri.decode(rawName)
                val schedule = Uri.decode(rawSchedule)
                val room = Uri.decode(rawRoom)

                ConfirmationScreen(
                    className = className,
                    schedule = schedule,
                    room = room,
                    onViewReservationsClick = {
                        navController.navigate(Screen.Reservations.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Screen.Reservations.route) {
                ReservationsScreen()
            }
            composable(Screen.Routines.route) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Rutinas")
                }
            }
            composable(Screen.Profile.route) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Perfil")
                }
            }
        }
    }
}
