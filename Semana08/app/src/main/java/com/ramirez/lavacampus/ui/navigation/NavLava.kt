package com.ramirez.lavacampus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ramirez.lavacampus.LavaApp
import com.ramirez.lavacampus.data.prefs.PreferenciasStore
import com.ramirez.lavacampus.data.repository.LavaRepositoryRoom
import com.ramirez.lavacampus.model.Rol
import com.ramirez.lavacampus.ui.estudiante.LoginScreen
import com.ramirez.lavacampus.ui.estudiante.SplashScreen
import com.ramirez.lavacampus.viewmodel.LavaViewModelFactory
import com.ramirez.lavacampus.viewmodel.SesionViewModel

@Composable
fun NavLava() {
    val context = LocalContext.current
    val app = context.applicationContext as LavaApp
    val prefs = PreferenciasStore(context)
    val repo = LavaRepositoryRoom(app.database)
    val factory = LavaViewModelFactory(repo, prefs)

    val navController = rememberNavController()
    val sesionViewModel: SesionViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = Rutas.Splash.ruta
    ) {
        composable(Rutas.Splash.ruta) {
            SplashScreen(
                vm = sesionViewModel,
                onNavegar = { rol ->
                    when (rol) {
                        Rol.CLIENTE.name -> navController.navigate(Rutas.EstudianteInicio.ruta) {
                            popUpTo(Rutas.Splash.ruta) { inclusive = true }
                        }
                        Rol.ADMIN.name -> navController.navigate(Rutas.AdminPanel.ruta) {
                            popUpTo(Rutas.Splash.ruta) { inclusive = true }
                        }
                        else -> navController.navigate(Rutas.Login.ruta) {
                            popUpTo(Rutas.Splash.ruta) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Rutas.Login.ruta) {
            LoginScreen(
                vm = sesionViewModel,
                onLoginExito = { rol ->
                    if (rol == Rol.CLIENTE.name) {
                        navController.navigate(Rutas.EstudianteInicio.ruta) {
                            popUpTo(Rutas.Login.ruta) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Rutas.AdminPanel.ruta) {
                            popUpTo(Rutas.Login.ruta) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}