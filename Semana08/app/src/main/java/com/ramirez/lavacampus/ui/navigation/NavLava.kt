package com.ramirez.lavacampus.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ramirez.lavacampus.LavaApp
import com.ramirez.lavacampus.data.local.entity.Usuario
import com.ramirez.lavacampus.data.prefs.PreferenciasStore
import com.ramirez.lavacampus.data.repository.LavaRepositoryRoom
import com.ramirez.lavacampus.model.Rol
import com.ramirez.lavacampus.ui.administrador.AjustesScreen
import com.ramirez.lavacampus.ui.administrador.BandejaScreen
import com.ramirez.lavacampus.ui.administrador.ClientesScreen
import com.ramirez.lavacampus.ui.administrador.FormularioServicioScreen
import com.ramirez.lavacampus.ui.administrador.GestionPedidoScreen
import com.ramirez.lavacampus.ui.administrador.PanelScreen
import com.ramirez.lavacampus.ui.administrador.ServiciosScreen
import com.ramirez.lavacampus.ui.components.AppBottomBar
import com.ramirez.lavacampus.ui.estudiante.DetallePedidoScreen
import com.ramirez.lavacampus.ui.estudiante.InicioScreen
import com.ramirez.lavacampus.ui.estudiante.LoginScreen
import com.ramirez.lavacampus.ui.estudiante.MisPedidosScreen
import com.ramirez.lavacampus.ui.estudiante.NuevoPedidoScreen
import com.ramirez.lavacampus.ui.estudiante.PerfilScreen
import com.ramirez.lavacampus.ui.estudiante.RegistroScreen
import com.ramirez.lavacampus.ui.estudiante.SplashScreen
import com.ramirez.lavacampus.ui.estudiante.TarifasScreen
import com.ramirez.lavacampus.ui.theme.LavaTheme
import com.ramirez.lavacampus.viewmodel.BandejaViewModel
import com.ramirez.lavacampus.viewmodel.ClientesViewModel
import com.ramirez.lavacampus.viewmodel.DetallePedidoViewModel
import com.ramirez.lavacampus.viewmodel.GestionPedidoViewModel
import com.ramirez.lavacampus.viewmodel.InicioViewModel
import com.ramirez.lavacampus.viewmodel.LavaViewModelFactory
import com.ramirez.lavacampus.viewmodel.MisPedidosViewModel
import com.ramirez.lavacampus.viewmodel.NuevoPedidoViewModel
import com.ramirez.lavacampus.viewmodel.PanelViewModel
import com.ramirez.lavacampus.viewmodel.RegistroViewModel
import com.ramirez.lavacampus.viewmodel.ServiciosViewModel
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    val usuarioActual by sesionViewModel.usuarioActual.collectAsState()
    val modoOscuro by sesionViewModel.modoOscuro.collectAsState()
    val rol = usuarioActual?.rol ?: Rol.CLIENTE.name

    val mostrarBottomBar = rutaActual in listOf(
        Rutas.EstudianteInicio.ruta,
        Rutas.EstudianteMisPedidos.ruta,
        Rutas.EstudianteTarifas.ruta,
        Rutas.EstudiantePerfil.ruta,
        Rutas.AdminPanel.ruta,
        Rutas.AdminBandeja.ruta,
        Rutas.AdminServicios.ruta,
        Rutas.AdminClientes.ruta,
        Rutas.AdminAjustes.ruta
    )

    LavaTheme(
        rol = rol,
        modoOscuro = modoOscuro
    ) {
        Scaffold(
            bottomBar = {
                if (mostrarBottomBar) {
                    AppBottomBar(
                        rol = rol,
                        rutaActual = rutaActual,
                        onNavegar = { ruta ->
                            navController.navigate(ruta) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Rutas.Splash.ruta,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Rutas.Splash.ruta) {
                    SplashScreen(
                        vm = sesionViewModel,
                        onIrALogin = {
                            navController.navigate(Rutas.Login.ruta) {
                                popUpTo(Rutas.Splash.ruta) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Rutas.Login.ruta) {
                    LoginScreen(
                        vm = sesionViewModel,
                        onLoginExito = { rolUsuario ->
                            if (rolUsuario == Rol.CLIENTE.name) {
                                navController.navigate(Rutas.EstudianteInicio.ruta) {
                                    popUpTo(Rutas.Login.ruta) { inclusive = true }
                                }
                            } else {
                                navController.navigate(Rutas.AdminPanel.ruta) {
                                    popUpTo(Rutas.Login.ruta) { inclusive = true }
                                }
                            }
                        },
                        onIrARegistro = {
                            navController.navigate(Rutas.Registro.ruta)
                        }
                    )
                }

                composable(Rutas.Registro.ruta) {
                    val registroViewModel: RegistroViewModel = viewModel(factory = factory)

                    RegistroScreen(
                        vm = registroViewModel,
                        onRegistroExito = {
                            // Regresa a LoginScreen para que el usuario inicie sesión con su correo y contraseña
                            navController.popBackStack()
                        },
                        onVolver = {
                            navController.popBackStack()
                        }
                    )
                }

                // === ESTUDIANTE ===
                composable(Rutas.EstudianteInicio.ruta) {
                    val usuario = usuarioActual ?: Usuario(1, "Ana Torres", "202310", "ana@correo.test", "1234", "CLIENTE")
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
                    val usuarioId = usuarioActual?.id ?: 1
                    val nuevoPedidoFactory = LavaViewModelFactory(repo, prefs, usuarioId)
                    val nuevoPedidoViewModel: NuevoPedidoViewModel = viewModel(factory = nuevoPedidoFactory)

                    NuevoPedidoScreen(
                        vm = nuevoPedidoViewModel,
                        onPedidoConfirmado = {
                            navController.navigate(Rutas.EstudianteMisPedidos.ruta) {
                                popUpTo(Rutas.EstudianteInicio.ruta)
                            }
                        },
                        onVolver = { navController.popBackStack() }
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
                    val usuarioId = usuarioActual?.id ?: 1
                    val misPedidosFactory = LavaViewModelFactory(repo, prefs, usuarioId)
                    val misPedidosViewModel: MisPedidosViewModel = viewModel(factory = misPedidosFactory)

                    TarifasScreen(
                        vm = misPedidosViewModel,
                        onVolver = { navController.popBackStack() }
                    )
                }

                composable(Rutas.EstudiantePerfil.ruta) {
                    val usuario = usuarioActual ?: Usuario(1, "Ana Torres", "202310", "ana@correo.test", "1234", "CLIENTE")

                    PerfilScreen(
                        usuario = usuario,
                        vm = sesionViewModel,
                        onCambiarPerfil = {
                            navController.navigate(Rutas.AdminPanel.ruta) {
                                popUpTo(Rutas.EstudianteInicio.ruta) { inclusive = true }
                            }
                        },
                        onCerrarSesion = {
                            navController.navigate(Rutas.Login.ruta) {
                                popUpTo(Rutas.EstudianteInicio.ruta) { inclusive = true }
                            }
                        }
                    )
                }

                // === ADMINISTRADOR ===
                composable(Rutas.AdminPanel.ruta) {
                    val panelViewModel: PanelViewModel = viewModel(factory = factory)

                    PanelScreen(
                        vm = panelViewModel,
                        onVerBandeja = { navController.navigate(Rutas.AdminBandeja.ruta) }
                    )
                }

                composable(Rutas.AdminBandeja.ruta) {
                    val bandejaViewModel: BandejaViewModel = viewModel(factory = factory)

                    BandejaScreen(
                        vm = bandejaViewModel,
                        onGestionarPedido = { pedidoId -> navController.navigate(Rutas.AdminGestionPedido.crearRuta(pedidoId)) }
                    )
                }

                composable(
                    route = Rutas.AdminGestionPedido.ruta,
                    arguments = listOf(navArgument("pedidoId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val pedidoId = backStackEntry.arguments?.getInt("pedidoId") ?: 0
                    val gestionFactory = LavaViewModelFactory(repo, prefs, pedidoId = pedidoId)
                    val gestionViewModel: GestionPedidoViewModel = viewModel(factory = gestionFactory)

                    GestionPedidoScreen(
                        vm = gestionViewModel,
                        onVolver = { navController.popBackStack() }
                    )
                }

                composable(Rutas.AdminServicios.ruta) {
                    val serviciosViewModel: ServiciosViewModel = viewModel(factory = factory)

                    ServiciosScreen(
                        vm = serviciosViewModel,
                        onEditarServicio = { id -> navController.navigate(Rutas.AdminFormularioServicio.crearRuta(id)) },
                        onNuevoServicio = { navController.navigate(Rutas.AdminFormularioServicio.crearRuta(0)) }
                    )
                }

                composable(
                    route = Rutas.AdminFormularioServicio.ruta,
                    arguments = listOf(navArgument("servicioId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val servicioId = backStackEntry.arguments?.getInt("servicioId") ?: 0
                    val serviciosViewModel: ServiciosViewModel = viewModel(factory = factory)

                    FormularioServicioScreen(
                        servicioId = servicioId,
                        vm = serviciosViewModel,
                        onVolver = { navController.popBackStack() }
                    )
                }

                composable(Rutas.AdminClientes.ruta) {
                    val clientesViewModel: ClientesViewModel = viewModel(factory = factory)

                    ClientesScreen(vm = clientesViewModel)
                }

                composable(Rutas.AdminAjustes.ruta) {
                    val usuario = usuarioActual ?: Usuario(4, "Administrador", "", "admin@lavacampus.test", "admin", "ADMIN")

                    AjustesScreen(
                        usuario = usuario,
                        vm = sesionViewModel,
                        onCambiarPerfil = {
                            navController.navigate(Rutas.EstudianteInicio.ruta) {
                                popUpTo(Rutas.AdminPanel.ruta) { inclusive = true }
                            }
                        },
                        onCerrarSesion = {
                            navController.navigate(Rutas.Login.ruta) {
                                popUpTo(Rutas.AdminPanel.ruta) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
