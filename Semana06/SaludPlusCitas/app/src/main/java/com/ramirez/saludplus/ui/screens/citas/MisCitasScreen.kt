package com.ramirez.saludplus.ui.screens.citas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ramirez.saludplus.data.repository.Repositorio
import com.ramirez.saludplus.navigation.Rutas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisCitasScreen(navController: NavHostController) {
    val usuarioId = Repositorio.usuarioActual?.id ?: 1
    val misCitas = Repositorio.getCitasPorUsuario(usuarioId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Citas Médicas", fontWeight = FontWeight.Bold, fontSize = 18.sp) }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = androidx.compose.ui.graphics.Color.White) {
                val navItemColors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = androidx.compose.ui.graphics.Color.Gray,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = androidx.compose.ui.graphics.Color.Gray,
                    indicatorColor = androidx.compose.ui.graphics.Color(0xFFDBEAFE)
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    selected = false,
                    onClick = { navController.navigate(Rutas.HOME) },
                    colors = navItemColors
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Citas") },
                    label = { Text("Citas") },
                    selected = true,
                    onClick = { /* Ya estamos aquí */ },
                    colors = navItemColors
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Description, contentDescription = "Resultados") },
                    label = { Text("Resultados") },
                    selected = false,
                    onClick = { navController.navigate(Rutas.RESULTADOS) },
                    colors = navItemColors
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = { navController.navigate(Rutas.PERFIL) },
                    colors = navItemColors
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Historial de citas agendadas",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (misCitas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.EventBusy,
                            contentDescription = null,
                            modifier = Modifier.size(60.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "No tienes citas registradas",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(misCitas) { cita ->
                        val medico = Repositorio.medicos.find { it.id == cita.medicoId }
                        val especialidad = Repositorio.especialidades.find { it.id == cita.especialidadId }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    Repositorio.citaSeleccionadaId = cita.id
                                    navController.navigate(Rutas.DETALLE_CITA)
                                },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = especialidad?.nombre ?: "Especialidad",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "Dr. ${medico?.nombre ?: "Médico"}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Text(
                                            text = "📅 ${cita.fecha}",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = "⏰ ${cita.hora}",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = "Ver detalle",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}