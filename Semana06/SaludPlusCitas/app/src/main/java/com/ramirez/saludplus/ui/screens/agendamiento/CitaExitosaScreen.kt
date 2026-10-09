package com.ramirez.saludplus.ui.screens.agendamiento

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ramirez.saludplus.data.repository.Repositorio
import com.ramirez.saludplus.navigation.Rutas

@Composable
fun CitaExitosaScreen(navController: NavHostController) {
    val medicoActual = Repositorio.medicos.find { it.id == Repositorio.medicoSeleccionadoId }
    val nombreMedico = medicoActual?.let { "Dr. ${it.nombre}" } ?: "Dr. Médico"

    val especialidadActual = Repositorio.especialidades.find { it.id == Repositorio.especialidadSeleccionadaId }
    val nombreEspecialidad = especialidadActual?.nombre ?: "Especialidad"

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Icono de éxito y título
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Éxito",
                    tint = Color(0xFF16A34A),
                    modifier = Modifier.size(190.dp)
                )
                Text(
                    text = "CITA REGISTRADA !!!",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                        text = "CON EXITO!!!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Resumen de la cita
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Detalles de tu reserva",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    Text(text = "Médico: $nombreMedico", fontSize = 18.sp)
                    Text(text = "Especialidad: $nombreEspecialidad", fontSize = 18.sp)
                    Text(text = "Fecha: ${Repositorio.fechaSeleccionada}", fontSize = 18.sp)
                    Text(text = "Hora: ${Repositorio.horaSeleccionada}", fontSize = 18.sp)
                }
            }

            // Botón de Volver al Home
            Button(
                onClick = {
                    navController.navigate(Rutas.HOME) {
                        popUpTo(Rutas.HOME) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Volver al inicio", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}