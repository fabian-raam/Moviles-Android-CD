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

// Modelo de datos para representar cada ítem de la barra de navegación inferior (Bottom Bar)
data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

// Función principal de navegación que contiene el Scaffold, BottomBar y el NavHost
@Composable
fun AppNavigation() {

    // Controlador de navegación para gestionar las transiciones entre pantallas
    val navController = rememberNavController()

    // Lista de reservas (Estado compartido en toda la navegación)
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

    // Definición de las pestañas que aparecerán en la barra de navegación inferior
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

    // Obtener la entrada actual de la pila de navegación para saber en qué pantalla estamos
    val navBackStackEntry by
    navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry?.destination?.route

    // Definición de colores personalizados para los elementos seleccionados de la barra
    val greenColor = Color(0xFF2E7D32)
    val lightGreenColor = Color(0xFFE8F5E9)

    // Scaffold principal que incluye la barra de navegación inferior (bottomBar) y el contenido dinámico
    Scaffold(

        bottomBar = {

            // Mostrar la barra de navegación inferior únicamente si la ruta actual coincide con alguna de las pestañas principales
            if (
                bottomNavItems.any {
                    it.route == currentRoute
                }
            ) {

                NavigationBar(
                    containerColor = Color.White
                ) {

                    // Iterar sobre cada ítem para renderizarlo en la barra
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

                            // Determinar si este ítem es el seleccionado actualmente
                            selected =
                                currentRoute == item.route,

                            // Colores para el icono, texto e indicador de selección
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

                            // Acción al hacer clic en un ítem de la barra de navegación
                            onClick = {

                                if (
                                    currentRoute != item.route
                                ) {

                                    navController.navigate(
                                        item.route
                                    ) {

                                        // Evitar acumular múltiples instancias de Home en la pila
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

        // NavHost para definir las rutas y pantallas disponibles en la aplicación
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            // Pantalla de Inicio (Home)
            composable(
                Screen.Home.route
            ) {

                HomeScreen(

                    onClassClick = { className ->

                        // Codificar el nombre de la clase para pasarla de forma segura por argumento en la ruta
                        val encodedName =
                            Uri.encode(className)

                        navController.navigate(
                            Screen.ClassDetail
                                .createRoute(encodedName)
                        )
                    }
                )
            }

            // Pantalla de Detalle de Clase
            composable(

                route = Screen.ClassDetail.route,

                arguments = listOf(

                    navArgument("className") {
                        type = NavType.StringType
                    }
                )

            ) { backStackEntry ->

                // Recuperar y decodificar el argumento de la clase seleccionada
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

                        // Comprobar si la clase ya está reservada con estado confirmada
                        val alreadyReserved =
                            reservations.any {

                                it.className == name &&
                                        it.status == "Confirmada"
                            }

                        // Agregar nueva reserva a la lista si no estaba reservada
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

                        // Navegar a la pantalla de confirmación pasando los datos codificados
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

            // Pantalla de Confirmación de Reserva
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

                // Obtener y decodificar todos los argumentos pasados a la confirmación
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

                        // Navegar a la pantalla de reservas y limpiar la pila hasta Home
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

            // Pantalla de Reservas
            composable(
                Screen.Reservations.route
            ) {

                ReservationsScreen(
                    reservations = reservations
                )
            }

            // Pantalla de Rutinas
            composable(
                Screen.Routines.route
            ) {

                RoutinesScreen(
                    reservations = reservations
                )
            }

            // Pantalla de Perfil
            composable(
                Screen.Profile.route
            ) {

                ProfileScreen(
                    classCount = reservations.size
                )
            }
        }
    }
}

