package com.ramirez.saludplus.ui.screens.agendamiento

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
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
fun FechaHoraScreen(navController: NavHostController) {
    // Obtener médico y especialidad seleccionada
    val medicoActual = Repositorio.medicos.find { it.id == Repositorio.medicoSeleccionadoId }
    val nombreMedico = medicoActual?.let { "Dr. ${it.nombre}" } ?: "Dr. Médico"

    val especialidadActual = Repositorio.especialidades.find { it.id == Repositorio.especialidadSeleccionadaId }
    val nombreEspecialidad = especialidadActual?.nombre ?: "Especialidad"

    val imagenDoctor = if (medicoActual?.nombre in listOf("Ana Torres", "Sofia Guevara", "Kiara Lopez", "Noemi Renez", "Paola Bala")) {
        R.drawable.doc_mujer
    } else {
        R.drawable.doc_hombre
    }

    // Estados para fecha y hora seleccionada
    val fechasDisponibles = listOf("Lun 15", "Mar 16", "Mié 17", "Jue 18", "Vie 19")
    var fechaSeleccionada by remember { mutableStateOf("Mar 16") }

    val horasBase = listOf("08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00")
    var horaSeleccionada by remember { mutableStateOf<String?>(null) }

    // LÓGICA REACTIVA: Un horario reservado deja de aparecer para ese médico y fecha
    val horasOcupadas = Repositorio.citas
        .filter { it.medicoId == Repositorio.medicoSeleccionadoId && it.fecha.contains(fechaSeleccionada) }
        .map { it.hora }

    val horasDisponibles = horasBase.filter { it !in horasOcupadas }

    // VALIDACIÓN: Continuar solo se habilita con día y hora elegidos
    val esValido = fechaSeleccionada.isNotBlank() && horaSeleccionada != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selección de fecha y hora", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
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
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Tarjeta informativa del doctor
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
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
                                .size(55.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Column {
                            Text(text = nombreMedico, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = nombreEspecialidad, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }

                // Selector de mes y días
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { }) {
                                Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = "Mes anterior")
                            }
                            Text(
                                text = "Setiembre 2026",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            IconButton(onClick = { }) {
                                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Mes siguiente")
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            fechasDisponibles.forEach { fecha ->
                                val esSeleccionado = fecha == fechaSeleccionada
                                Surface(
                                    modifier = Modifier
                                        .size(width = 50.dp, height = 65.dp)
                                        .clickable {
                                            fechaSeleccionada = fecha
                                            horaSeleccionada = null // Limpiar hora al cambiar de día
                                        },
                                    shape = RoundedCornerShape(14.dp),
                                    color = if (esSeleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = fecha.split(" ")[0],
                                            fontSize = 12.sp,
                                            color = if (esSeleccionado) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = fecha.split(" ")[1],
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (esSeleccionado) Color.White else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Selector de horas con LazyVerticalGrid reactivo
                Text(
                    text = "Horarios disponibles",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                if (horasDisponibles.isEmpty()) {
                    Text(
                        text = "No hay horarios disponibles para esta fecha.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.height(180.dp)
                    ) {
                        items(horasDisponibles) { hora ->
                            val esSeleccionado = hora == horaSeleccionada
                            OutlinedButton(
                                onClick = { horaSeleccionada = hora },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (esSeleccionado) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    contentColor = if (esSeleccionado) Color.White else MaterialTheme.colorScheme.onSurface
                                ),
                                border = if (esSeleccionado) null else ButtonDefaults.outlinedButtonBorder
                            ) {
                                Text(text = hora, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }

            // Botón Continuar (habilitado solo cuando hay fecha y hora)
            Button(
                onClick = {
                    Repositorio.fechaSeleccionada = "Martes $fechaSeleccionada de setiembre 2026"
                    Repositorio.horaSeleccionada = horaSeleccionada ?: "09:30"
                    navController.navigate(Rutas.CONFIRMAR_CITA)
                },
                enabled = esValido,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Continuar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}