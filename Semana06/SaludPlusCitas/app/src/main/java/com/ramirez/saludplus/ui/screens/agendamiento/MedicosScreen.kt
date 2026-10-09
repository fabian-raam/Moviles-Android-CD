package com.ramirez.saludplus.ui.screens.agendamiento

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ramirez.saludplus.R
import com.ramirez.saludplus.data.repository.Repositorio
import com.ramirez.saludplus.navigation.Rutas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicosScreen(navController: NavHostController) {
    // Obtener la especialidad seleccionada actualmente
    val especialidadActual = Repositorio.especialidades.find { it.id == Repositorio.especialidadSeleccionadaId }
    val nombreEspecialidad = especialidadActual?.nombre ?: "Especialidad"

    // Obtener los médicos de esa especialidad desde el repositorio
    val medicosLista = Repositorio.getMedicosPorEspecialidad(Repositorio.especialidadSeleccionadaId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Médicos de $nombreEspecialidad", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Acción de búsqueda */ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
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

            if (medicosLista.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay médicos disponibles para esta especialidad",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 15.sp
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(medicosLista) { medico ->
                        // Asignar foto de doctor o doctora según el nombre
                        val imagenDoctor = if (medico.nombre in listOf("Ana Torres", "Sofia Guevara", "Kiara Lopez", "Noemi Renez", "Paola Bala")) {
                            R.drawable.doc_mujer
                        } else {
                            R.drawable.doc_hombre
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Guardar el médico seleccionado y navegar a Fecha y Hora
                                    Repositorio.medicoSeleccionadoId = medico.id
                                    navController.navigate(Rutas.FECHA_HORA)
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
                                    // Foto real del doctor/doctora
                                    Image(
                                        painter = painterResource(id = imagenDoctor),
                                        contentDescription = "Foto de ${medico.nombre}",
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )

                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = "Dr. ${medico.nombre}",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = nombreEspecialidad,
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = "Calificación",
                                                tint = Color(0xFFF59E0B),
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(
                                                text = "4.8 (95)",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }

                                // Badge de disponibilidad verde
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFDCFCE7),
                                    modifier = Modifier.padding(start = 4.dp)
                                ) {
                                    Text(
                                        text = "Disponible hoy",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF16A34A),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
