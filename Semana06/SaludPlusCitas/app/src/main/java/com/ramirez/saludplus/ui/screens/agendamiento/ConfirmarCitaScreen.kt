package com.ramirez.saludplus.ui.screens.agendamiento

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.ramirez.saludplus.data.model.Cita
import com.ramirez.saludplus.data.repository.Repositorio
import com.ramirez.saludplus.navigation.Rutas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmarCitaScreen(navController: NavHostController) {
    // Obtener datos del repositorio
    val medicoActual = Repositorio.medicos.find { it.id == Repositorio.medicoSeleccionadoId }
    val nombreMedico = medicoActual?.let { "Dr. ${it.nombre}" } ?: "Dr. Médico"

    val especialidadActual = Repositorio.especialidades.find { it.id == Repositorio.especialidadSeleccionadaId }
    val nombreEspecialidad = especialidadActual?.nombre ?: "Especialidad"

    val imagenDoctor = if (medicoActual?.nombre in listOf("Ana Torres", "Sofia Guevara", "Kiara Lopez", "Noemi Renez", "Paola Bala")) {
        R.drawable.doc_mujer
    } else {
        R.drawable.doc_hombre
    }

    var motivoConsulta by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirmar cita", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
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
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Tarjeta del Doctor
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
                            Text(text = nombreMedico, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = nombreEspecialidad, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = "CMP: 12345", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }

                // Detalles de la cita (Fecha, Hora, Tipo, Dirección)
                DetalleItem(icon = Icons.Default.CalendarToday, titulo = "Fecha", valor = Repositorio.fechaSeleccionada)
                DetalleItem(icon = Icons.Default.Schedule, titulo = "Hora", valor = Repositorio.horaSeleccionada)
                DetalleItem(icon = Icons.Default.LocalHospital, titulo = "Tipo de atención", valor = "Consulta presencial")
                DetalleItem(icon = Icons.Default.LocationOn, titulo = "Dirección", valor = "Av. Los Olivos 123, Lima")

                Spacer(modifier = Modifier.height(2.dp))

                // Motivo de consulta
                Text(
                    text = "Motivo de consulta (opcional)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OutlinedTextField(
                    value = motivoConsulta,
                    onValueChange = { motivoConsulta = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Botón Agendar cita (Guarda en Repositorio y va a éxito)
            Button(
                onClick = {
                    val nuevaCita = Cita(
                        id = Repositorio.citas.size + 1,
                        usuarioId = Repositorio.usuarioActual?.id ?: 1,
                        medicoId = Repositorio.medicoSeleccionadoId,
                        especialidadId = Repositorio.especialidadSeleccionadaId,
                        fecha = Repositorio.fechaSeleccionada,
                        hora = Repositorio.horaSeleccionada
                    )
                    Repositorio.agregarCita(nuevaCita)
                    navController.navigate(Rutas.CITA_EXITOSA) {
                        popUpTo(Rutas.HOME)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Agendar cita", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

// Componente auxiliar reutilizable para las filas de detalles
@Composable
fun DetalleItem(icon: ImageVector, titulo: String, valor: String) {
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