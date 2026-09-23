package com.ramirez.citas

import android.net.Uri
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
                    navController.navigate(Screen.Home.route)
                },

                onAppointmentsClick = {
                    navController.navigate(Screen.Appointments.route)
                },

                onHistoryClick = {
                    navController.navigate(Screen.MedicalHistory.route)
                },

                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
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

            val doctorName =
                backStackEntry.arguments?.getString("doctorName") ?: ""

            DoctorDetailScreen(

                doctorName = Uri.decode(doctorName),

                onBackClick = {
                    navController.popBackStack()
                },

                onAppointmentClick = {
                    navController.navigate(Screen.Appointment.route)
                }
            )
        }

        // AGENDAR CITA
        composable(Screen.Appointment.route) {

            AppointmentScreen(

                onConfirmClick = {
                    navController.navigate(
                        Screen.Confirmation.route
                    )
                }
            )
        }

        // CONFIRMACIÓN
        composable(Screen.Confirmation.route) {

            ConfirmationScreen(

                onAppointmentsClick = {
                    navController.navigate(
                        Screen.Appointments.route
                    )
                }
            )
        }

        // MIS CITAS
        composable(Screen.Appointments.route) {

            AppointmentsScreen()
        }

        // HISTORIAL
        composable(Screen.MedicalHistory.route) {

            Text(
                text = "Historial médico"
            )
        }

        // PERFIL
        composable(Screen.Profile.route) {

            Text(
                text = "Perfil"
            )
        }
    }
}