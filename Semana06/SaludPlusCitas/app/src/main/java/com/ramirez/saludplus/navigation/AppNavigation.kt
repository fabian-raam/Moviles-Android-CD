package com.ramirez.saludplus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ramirez.saludplus.ui.screens.agendamiento.*
import com.ramirez.saludplus.ui.screens.auth.*
import com.ramirez.saludplus.ui.screens.citas.*
import com.ramirez.saludplus.ui.screens.home.*
import com.ramirez.saludplus.ui.screens.notificaciones.*
import com.ramirez.saludplus.ui.screens.perfil.*
import com.ramirez.saludplus.ui.screens.resultados.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rutas.SPLASH) {
        //compable sirve para decirle al compilador que aqui abra
        //una parada,una vista, una ruta
        //luego pasamos la ruta y luego mostramos que vista le
        //pertenece a esa ruta y por ultimo a las vistas
        //les pasamos navController para que puedan usar funciones
        //de ir a otra vista
        // Auth
        composable(Rutas.SPLASH) { SplashScreen(navController) }
        composable(Rutas.LOGIN) { LoginScreen(navController) }
        composable(Rutas.REGISTRO) { RegistroScreen(navController) }
        composable(Rutas.TERMINOS) { TerminosScreen(navController) }

        // Home
        composable(Rutas.HOME) { HomeScreen(navController) }

        // Agendamiento
        composable(Rutas.ESPECIALIDADES) { EspecialidadesScreen(navController) }
        composable(Rutas.MEDICOS) { MedicosScreen(navController) }
        composable(Rutas.FECHA_HORA) { FechaHoraScreen(navController) }
        composable(Rutas.CONFIRMAR_CITA) { ConfirmarCitaScreen(navController) }
        composable(Rutas.CITA_EXITOSA) { CitaExitosaScreen(navController) }

        // Citas
        composable(Rutas.MIS_CITAS) { MisCitasScreen(navController) }
        composable(Rutas.DETALLE_CITA) { DetalleCitaScreen(navController) }

        // Perfil, Resultados, Notificaciones
        composable(Rutas.PERFIL) { PerfilScreen(navController) }
        composable(Rutas.RESULTADOS) { ResultadosScreen(navController) }
        composable(Rutas.NOTIFICACIONES) { NotificacionesScreen(navController) }
    }
}
