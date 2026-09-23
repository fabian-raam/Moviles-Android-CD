package com.ramirez.citas

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

        // INICIO
        composable(Screen.Home.route) {

            HomeScreen(
                onDoctorClick = { doctorName ->
                    navController.navigate(
                        Screen.DoctorDetail.createRoute(
                            Uri.encode(doctorName)
                        )
                    )
                },

                onHomeClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },

                onAppointmentsClick = {
                    navController.navigate(Screen.Appointments.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },

                onHistoryClick = {
                    navController.navigate(Screen.MedicalHistory.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },

                onProfileClick = {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        // PERFIL DEL MÉDICO
        composable(
            route = Screen.DoctorDetail.route,
            arguments = listOf(
                navArgument("doctorName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val doctorName = Uri.decode(
                backStackEntry.arguments
                    ?.getString("doctorName") ?: ""
            )

            DoctorDetailScreen(
                doctorName = doctorName,

                onBackClick = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Screen.Home.route)
                    }
                },

                onAppointmentClick = {
                    navController.navigate(
                        Screen.Appointment.createRoute(
                            Uri.encode(doctorName)
                        )
                    )
                }
            )
        }

        // AGENDAR CITA
        composable(
            route = Screen.Appointment.route,
            arguments = listOf(
                navArgument("doctorName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val doctorName = Uri.decode(
                backStackEntry.arguments
                    ?.getString("doctorName") ?: ""
            )

            AppointmentScreen(
                doctorName = doctorName,

                onBackClick = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Screen.Home.route)
                    }
                },

                onConfirmClick = { date, time ->

                    navController.navigate(
                        Screen.Confirmation.createRoute(
                            Uri.encode(doctorName),
                            Uri.encode(date),
                            Uri.encode(time)
                        )
                    ) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }

        // CONFIRMACIÓN
        composable(
            route = Screen.Confirmation.route,

            arguments = listOf(
                navArgument("doctorName") {
                    type = NavType.StringType
                },
                navArgument("date") {
                    type = NavType.StringType
                },
                navArgument("time") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val doctorName = Uri.decode(
                backStackEntry.arguments
                    ?.getString("doctorName") ?: ""
            )

            val date = Uri.decode(
                backStackEntry.arguments
                    ?.getString("date") ?: ""
            )

            val time = Uri.decode(
                backStackEntry.arguments
                    ?.getString("time") ?: ""
            )

            ConfirmationScreen(
                doctorName = doctorName,
                date = date,
                time = time,

                onHomeClick = {
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                },

                onAppointmentsClick = {
                    navController.navigate(
                        Screen.Appointments.route
                    ) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }

        // MIS CITAS
        composable(Screen.Appointments.route) {

            AppointmentsScreen(
                onBackClick = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Screen.Home.route)
                    }
                }
            )
        }

        // HISTORIAL
        composable(Screen.MedicalHistory.route) {

            SimpleScreen(
                title = "Historial médico",
                message = "Aquí podrás consultar tu historial médico.",
                onBackClick = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Screen.Home.route)
                    }
                }
            )
        }

        // PERFIL
        composable(Screen.Profile.route) {

            SimpleScreen(
                title = "Perfil",
                message = "Juan Pérez\nPaciente",
                onBackClick = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Screen.Home.route)
                    }
                }
            )
        }
    }
}


@Composable
fun SimpleScreen(
    title: String,
    message: String,
    onBackClick: () -> Unit
) {

    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        androidx.compose.material3.TextButton(
            onClick = onBackClick
        ) {
            Text("← Volver")
        }

        Text(
            text = title,
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = message,
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
