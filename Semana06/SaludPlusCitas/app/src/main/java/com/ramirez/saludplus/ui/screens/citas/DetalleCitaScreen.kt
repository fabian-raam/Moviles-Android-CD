package com.ramirez.saludplus.ui.screens.citas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ramirez.saludplus.R
import com.ramirez.saludplus.data.repository.Repositorio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleCitaScreen(navController: NavHostController) {
    val cita = Repositorio.citas.find { it.id == Repositorio.citaSeleccionadaId }
    val medico = Repositorio.medicos.find { it.id == cita?.medicoId }
    val especialidad = Repositorio.especialidades.find { it.id == cita?.especialidadId }

    val imagenDoctor = if (medico?.nombre in listOf("Ana Torres", "Sofia Guevara", "Kiara Lopez", "Noemi Renez", "Paola Bala")) {
        R.drawable.doc_mujer
    } else {
        R.drawable.doc_hombre
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Cita", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
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
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (cita == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontró la información de la cita.")
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Tarjeta del Doctor idéntica a Confirmar Cita
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = imagenDoctor),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Column {
                                Text(text = "Dr. ${medico?.nombre ?: "Médico"}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Text(text = especialidad?.nombre ?: "Especialidad", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(text = "CMP: 12345", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }

                    // Detalles con tarjetas e iconos idénticos
                    DetalleItemCita(icon = Icons.Default.CalendarToday, titulo = "Fecha", valor = cita.fecha)
                    DetalleItemCita(icon = Icons.Default.Schedule, titulo = "Hora", valor = cita.hora)
                    DetalleItemCita(icon = Icons.Default.LocalHospital, titulo = "Tipo de atención", valor = "Consulta presencial")
                    DetalleItemCita(icon = Icons.Default.LocationOn, titulo = "Dirección", valor = "Av. Los Olivos 123, Lima")
                }
            }

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Volver a mis citas", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun DetalleItemCita(icon: ImageVector, titulo: String, valor: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                }
            }
            Column {
                Text(text = titulo, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = valor, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}