package com.ramirez.lavacampus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ramirez.lavacampus.LavaApp
import com.ramirez.lavacampus.data.local.entity.Usuario
import com.ramirez.lavacampus.data.prefs.PreferenciasStore
import com.ramirez.lavacampus.data.repository.LavaRepositoryRoom
import com.ramirez.lavacampus.model.Rol
import com.ramirez.lavacampus.ui.estudiante.DetallePedidoScreen
import com.ramirez.lavacampus.ui.estudiante.InicioScreen
import com.ramirez.lavacampus.ui.estudiante.LoginScreen
import com.ramirez.lavacampus.ui.estudiante.MisPedidosScreen
import com.ramirez.lavacampus.ui.estudiante.NuevoPedidoScreen
import com.ramirez.lavacampus.ui.estudiante.SplashScreen
import com.ramirez.lavacampus.ui.estudiante.TarifasScreen
import com.ramirez.lavacampus.viewmodel.DetallePedidoViewModel
import com.ramirez.lavacampus.viewmodel.InicioViewModel
import com.ramirez.lavacampus.viewmodel.LavaViewModelFactory
import com.ramirez.lavacampus.viewmodel.MisPedidosViewModel
import com.ramirez.lavacampus.viewmodel.NuevoPedidoViewModel
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

        composable(Rutas.EstudianteInicio.ruta) {
            val usuarioActual by sesionViewModel.usuarioActual.collectAsState()
            val usuario = usuarioActual ?: Usuario(1, "Ana Torres", "U202310234", "ana@correo.test", "1234", "CLIENTE")
            val inicioFactory = LavaViewModelFactory(repo, prefs, usuario.id)
            val inicioViewModel: InicioViewModel = viewModel(factory = inicioFactory)

            InicioScreen(
                usuario = usuario,
                vm = inicioViewModel,
                onNuevoPedido = { navController.navigate(Rutas.EstudianteNuevoPedido.ruta) },
                onVerPedidos = { navController.navigate(Rutas.EstudianteMisPedidos.ruta) },
                onVerTarifas = { navController.navigate(Rutas.EstudianteTarifas.ruta) },
                onVerDetalle = { pedidoId -> navController.navigate(Rutas.EstudianteDetallePedido.crearRuta(pedidoId)) }
            )
        }

        composable(Rutas.EstudianteNuevoPedido.ruta) {
            val usuarioActual by sesionViewModel.usuarioActual.collectAsState()
            val usuarioId = usuarioActual?.id ?: 1
            val nuevoPedidoFactory = LavaViewModelFactory(repo, prefs, usuarioId)
            val nuevoPedidoViewModel: NuevoPedidoViewModel = viewModel(factory = nuevoPedidoFactory)

            NuevoPedidoScreen(
                vm = nuevoPedidoViewModel,
                onPedidoConfirmado = {
                    navController.navigate(Rutas.EstudianteMisPedidos.ruta) {
                        popUpTo(Rutas.EstudianteInicio.ruta)
                    }
                }
            )
        }

        composable(
            route = Rutas.EstudianteDetallePedido.ruta,
            arguments = listOf(navArgument("pedidoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pedidoId = backStackEntry.arguments?.getInt("pedidoId") ?: 0
            val detalleFactory = LavaViewModelFactory(repo, prefs, pedidoId = pedidoId)
            val detalleViewModel: DetallePedidoViewModel = viewModel(factory = detalleFactory)

            DetallePedidoScreen(
                vm = detalleViewModel,
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.EstudianteMisPedidos.ruta) {
            val usuarioActual by sesionViewModel.usuarioActual.collectAsState()
            val usuarioId = usuarioActual?.id ?: 1
            val misPedidosFactory = LavaViewModelFactory(repo, prefs, usuarioId)
            val misPedidosViewModel: MisPedidosViewModel = viewModel(factory = misPedidosFactory)

            MisPedidosScreen(
                vm = misPedidosViewModel,
                onNuevoPedido = { navController.navigate(Rutas.EstudianteNuevoPedido.ruta) },
                onVerDetalle = { pedidoId -> navController.navigate(Rutas.EstudianteDetallePedido.crearRuta(pedidoId)) },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.EstudianteTarifas.ruta) {
            val usuarioActual by sesionViewModel.usuarioActual.collectAsState()
            val usuarioId = usuarioActual?.id ?: 1
            val misPedidosFactory = LavaViewModelFactory(repo, prefs, usuarioId)
            val misPedidosViewModel: MisPedidosViewModel = viewModel(factory = misPedidosFactory)

            TarifasScreen(
                vm = misPedidosViewModel,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}