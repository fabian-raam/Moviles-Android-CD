package com.ramirez.lavacampus.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramirez.lavacampus.model.Rol
import com.ramirez.lavacampus.ui.navigation.Rutas

private data class ItemBottomBar(
    val titulo: String,
    val ruta: String,
    val icono: ImageVector
)

@Composable
fun AppBottomBar(
    rol: String,
    rutaActual: String?,
    onNavegar: (String) -> Unit
) {
    val items = if (rol == Rol.CLIENTE.name) {
        listOf(
            ItemBottomBar("Inicio", Rutas.EstudianteInicio.ruta, Icons.Default.Home),
            ItemBottomBar("Pedidos", Rutas.EstudianteMisPedidos.ruta, Icons.Default.List),
            ItemBottomBar("Tarifas", Rutas.EstudianteTarifas.ruta, Icons.Default.Sell),
            ItemBottomBar("Perfil", Rutas.EstudiantePerfil.ruta, Icons.Default.Person)
        )
    } else {
        listOf(
            ItemBottomBar("Panel", Rutas.AdminPanel.ruta, Icons.Default.Analytics),
            ItemBottomBar("Pedidos", Rutas.AdminBandeja.ruta, Icons.Default.Inbox),
            ItemBottomBar("Servicios", Rutas.AdminServicios.ruta, Icons.Default.LocalLaundryService),
            ItemBottomBar("Clientes", Rutas.AdminClientes.ruta, Icons.Default.People),
            ItemBottomBar("Ajustes", Rutas.AdminAjustes.ruta, Icons.Default.Person)
        )
    }

    NavigationBar {
        items.forEach { item ->
            val seleccionado = rutaActual == item.ruta
            NavigationBarItem(
                selected = seleccionado,
                onClick = {
                    if (!seleccionado) onNavegar(item.ruta)
                },
                icon = { Icon(imageVector = item.icono, contentDescription = item.titulo) },
                label = { Text(item.titulo) }
            )
        }
    }
}
