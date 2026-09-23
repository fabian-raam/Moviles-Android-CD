package com.ramirez.tecfit

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

    // ==========================================
    // LISTA DE RESERVAS
    // ==========================================

    val reservations = remember {

        mutableStateListOf(

            Reservation(
                className = "Cross Training",
                schedule = "Hoy, 6:00 pm",
                status = "Confirmada"
            ),

            Reservation(
                className = "Yoga funcional",
                schedule = "Ayer, 7:00 am",
                status = "Completada"
            )
        )
    }

    // ==========================================
    // BOTTOM BAR
    // ==========================================

    val bottomNavItems = listOf(

        BottomNavItem(
            title = "Inicio",
            route = Screen.Home.route,
            icon = Icons.Default.Home
        ),

        BottomNavItem(
            title = "Reservas",
            route = Screen.Reservations.route,
            icon = Icons.Default.DateRange
        ),

        BottomNavItem(
            title = "Rutinas",
            route = Screen.Routines.route,
            icon = Icons.AutoMirrored.Filled.List
        ),

        BottomNavItem(
            title = "Perfil",
            route = Screen.Profile.route,
            icon = Icons.Default.Person
        )
    )

    val navBackStackEntry by
    navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry?.destination?.route

    val greenColor = Color(0xFF2E7D32)
    val lightGreenColor = Color(0xFFE8F5E9)

    Scaffold(

        bottomBar = {

            if (
                bottomNavItems.any {
                    it.route == currentRoute
                }
            ) {

                NavigationBar(
                    containerColor = Color.White
                ) {

                    bottomNavItems.forEach { item ->

                        NavigationBarItem(

                            icon = {

                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },

                            label = {

                                Text(
                                    text = item.title
                                )
                            },

                            selected =
                                currentRoute == item.route,

                            colors =
                                NavigationBarItemDefaults.colors(

                                    selectedIconColor =
                                        greenColor,

                                    selectedTextColor =
                                        greenColor,

                                    indicatorColor =
                                        lightGreenColor,

                                    unselectedIconColor =
                                        Color.Gray,

                                    unselectedTextColor =
                                        Color.Gray
                                ),

                            onClick = {

                                if (
                                    currentRoute != item.route
                                ) {

                                    navController.navigate(
                                        item.route
                                    ) {

                                        popUpTo(
                                            Screen.Home.route
                                        ) {
                                            inclusive = false
                                        }

                                        launchSingleTop = true
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

            // ==========================================
            // INICIO
            // ==========================================

            composable(
                Screen.Home.route
            ) {

                HomeScreen(

                    onClassClick = { className ->

                        val encodedName =
                            Uri.encode(className)

                        navController.navigate(
                            Screen.ClassDetail
                                .createRoute(encodedName)
                        )
                    }
                )
            }

            // ==========================================
            // DETALLE DE CLASE
            // ==========================================

            composable(

                route = Screen.ClassDetail.route,

                arguments = listOf(

                    navArgument("className") {
                        type = NavType.StringType
                    }
                )

            ) { backStackEntry ->

                val rawName =
                    backStackEntry.arguments
                        ?.getString("className")
                        ?: ""

                val className =
                    Uri.decode(rawName)

                ClassDetailScreen(

                    className = className,

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onReserveClick = {
                            name,
                            schedule,
                            room ->

                        // ==================================
                        // AGREGAR RESERVA
                        // ==================================

                        val alreadyReserved =
                            reservations.any {
                                it.className == name &&
                                        it.status == "Confirmada"
                            }

                        if (!alreadyReserved) {

                            reservations.add(
                                0,
                                Reservation(
                                    className = name,
                                    schedule = "Hoy, $schedule",
                                    status = "Confirmada"
                                )
                            )
                        }

                        // ==================================
                        // IR A CONFIRMACION
                        // ==================================

                        val route =
                            Screen.Confirmation.createRoute(

                                className =
                                    Uri.encode(name),

                                schedule =
                                    Uri.encode(schedule),

                                room =
                                    Uri.encode(room)
                            )

                        navController.navigate(route)
                    }
                )
            }

            // ==========================================
            // CONFIRMACION
            // ==========================================

            composable(

                route = Screen.Confirmation.route,

                arguments = listOf(

                    navArgument("className") {
                        type = NavType.StringType
                    },

                    navArgument("schedule") {
                        type = NavType.StringType
                    },

                    navArgument("room") {
                        type = NavType.StringType
                    }
                )

            ) { backStackEntry ->

                val rawName =
                    backStackEntry.arguments
                        ?.getString("className")
                        ?: ""

                val rawSchedule =
                    backStackEntry.arguments
                        ?.getString("schedule")
                        ?: ""

                val rawRoom =
                    backStackEntry.arguments
                        ?.getString("room")
                        ?: ""

                val className =
                    Uri.decode(rawName)

                val schedule =
                    Uri.decode(rawSchedule)

                val room =
                    Uri.decode(rawRoom)

                ConfirmationScreen(

                    className = className,

                    schedule = schedule,

                    room = room,

                    onViewReservationsClick = {

                        navController.navigate(
                            Screen.Reservations.route
                        ) {

                            popUpTo(
                                Screen.Home.route
                            ) {
                                inclusive = false
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            // ==========================================
            // RESERVAS
            // ==========================================

            composable(
                Screen.Reservations.route
            ) {

                ReservationsScreen(
                    reservations = reservations
                )
            }

            // ==========================================
            // RUTINAS
            // ==========================================

            composable(
                Screen.Routines.route
            ) {

                RoutinesScreen()
            }

            // ==========================================
            // PERFIL
            // ==========================================

            composable(
                Screen.Profile.route
            ) {

                ProfileScreen()
            }
        }
    }
}