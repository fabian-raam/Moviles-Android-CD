package com.ramirez.saludplus.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ramirez.saludplus.data.repository.Repositorio
import com.ramirez.saludplus.navigation.Rutas

@Composable
fun HomeScreen(navController: NavHostController) {
    val nombreUsuario = Repositorio.usuarioActual?.nombre ?: "Usuario"

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                val navItemColors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFFDBEAFE) // Pastelería azul para el pill indicador
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    selected = true,
                    onClick = { /* Ya estamos aquí */ },
                    colors = navItemColors
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Citas") },
                    label = { Text("Citas") },
                    selected = false,
                    onClick = { navController.navigate(Rutas.MIS_CITAS) },
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
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            // Cabecera con saludo y campana de notificaciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "¡Hola, $nombreUsuario!",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "¿Qué deseas hacer hoy?",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = { navController.navigate(Rutas.NOTIFICACIONES) }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificaciones",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            // Tarjetas de acceso rápido (Grid 2x2 con colores pasteles exactos del diseño)
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Agendar Cita (Azul pastel)
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(155.dp)
                            .clickable { navController.navigate(Rutas.ESPECIALIDADES) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2FE))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = Color(0xFF2563EB),
                                modifier = Modifier.size(38.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                "Agendar cita",
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                color = Color(0xFF1E3A8A)
                            )
                        }
                    }

                    // Mis Citas (Verde pastel)
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(155.dp)
                            .clickable { navController.navigate(Rutas.MIS_CITAS) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFDCFCE7))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.EventNote,
                                contentDescription = null,
                                tint = Color(0xFF16A34A),
                                modifier = Modifier.size(38.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                "Mis citas",
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                color = Color(0xFF14532D)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Mis Datos / Perfil (Morado pastel)
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(155.dp)
                            .clickable { navController.navigate(Rutas.PERFIL) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E8FF))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = Color(0xFF9333EA),
                                modifier = Modifier.size(38.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                "Mis datos",
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                color = Color(0xFF581C87)
                            )
                        }
                    }

                    // Resultados (Naranja/Melón pastel)
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(155.dp)
                            .clickable { navController.navigate(Rutas.RESULTADOS) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEDD5))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Assignment,
                                contentDescription = null,
                                tint = Color(0xFFD97706),
                                modifier = Modifier.size(38.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                "Resultados",
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                color = Color(0xFF78350F)
                            )
                        }
                    }
                }
            }

            // Sección: Especialidades destacadas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Especialidades destacadas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { navController.navigate(Rutas.ESPECIALIDADES) }) {
                    Text("Ver todas")
                }
            }

            // Lista horizontal de especialidades desde el Repositorio
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(Repositorio.especialidades) { especialidad ->
                    OutlinedCard(
                        modifier = Modifier
                            .width(115.dp)
                            .height(100.dp)
                            .clickable { navController.navigate(Rutas.ESPECIALIDADES) },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.MedicalServices,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = especialidad.nombre,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}
