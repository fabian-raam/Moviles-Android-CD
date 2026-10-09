package com.ramirez.saludplus.ui.screens.agendamiento

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ramirez.saludplus.data.repository.Repositorio
import com.ramirez.saludplus.navigation.Rutas

// Clase de ayuda para asignar diseño personalizado por ID de especialidad
data class EspecialidadUIInfo(
    val descripcion: String,
    val icon: ImageVector,
    val containerColor: Color,
    val contentColor: Color
)

fun getEspecialidadInfo(id: Int): EspecialidadUIInfo {
    return when (id) {
        1 -> EspecialidadUIInfo("Atención integral", Icons.Default.Person, Color(0xFFE0F2FE), Color(0xFF2563EB))
        2 -> EspecialidadUIInfo("Niños y adolescentes", Icons.Default.Face, Color(0xFFFFEDD5), Color(0xFFD97706))
        3 -> EspecialidadUIInfo("Salud de la mujer", Icons.Default.Favorite, Color(0xFFFCE7F3), Color(0xFFDB2777))
        4 -> EspecialidadUIInfo("Corazón y vasos sanguíneos", Icons.Default.MonitorHeart, Color(0xFFFEE2E2), Color(0xFFDC2626))
        5 -> EspecialidadUIInfo("Piel, cabello y uñas", Icons.Default.Spa, Color(0xFFFEF3C7), Color(0xFFB45309))
        6 -> EspecialidadUIInfo("Huesos y articulaciones", Icons.Default.Healing, Color(0xFFE0F2FE), Color(0xFF0284C7))
        7 -> EspecialidadUIInfo("Salud visual", Icons.Default.Visibility, Color(0xFFEEF2FF), Color(0xFF4F46E5))
        else -> EspecialidadUIInfo("Atención especializada", Icons.Default.MedicalServices, Color(0xFFF1F5F9), Color(0xFF475569))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EspecialidadesScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }

    // Filtrar especialidades según la búsqueda
    val especialidadesFiltradas = Repositorio.especialidades.filter {
        it.nombre.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Especialidades", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar especialidad...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            // Lista de especialidades
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(especialidadesFiltradas) { especialidad ->
                    val info = getEspecialidadInfo(especialidad.id)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Guardar el ID en el repositorio y navegar a Médicos
                                Repositorio.especialidadSeleccionadaId = especialidad.id
                                navController.navigate(Rutas.MEDICOS)
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = info.containerColor,
                                    modifier = Modifier.size(52.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = info.icon,
                                            contentDescription = null,
                                            tint = info.contentColor,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }
                                }
                                Column {
                                    Text(
                                        text = especialidad.nombre,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = info.descripcion,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Seleccionar",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}