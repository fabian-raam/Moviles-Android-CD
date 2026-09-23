package com.ramirez.tecfit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

val classDetailList = listOf(
    GymClass(
        name = "Yoga funcional",
        schedule = "7:00 am",
        room = "Sala 2",
        duration = "60 min",
        availableSlots = 10,
        totalSlots = 15,
        description = "Clase enfocada en movilidad, equilibrio y fortalecimiento corporal."
    ),
    GymClass(
        name = "Cross Training",
        schedule = "6:00 pm",
        room = "Sala 1",
        duration = "45 min",
        availableSlots = 8,
        totalSlots = 12,
        description = "Entrenamiento funcional de alta intensidad. Cupos limitados."
    ),
    GymClass(
        name = "Spinning",
        schedule = "7:30 pm",
        room = "Sala 3",
        duration = "50 min",
        availableSlots = 6,
        totalSlots = 10,
        description = "Entrenamiento cardiovascular en bicicleta con diferentes niveles de intensidad."
    )
)

@Composable
fun ClassDetailScreen(
    className: String,
    onBackClick: () -> Unit = {}
) {
    val gymClass = classDetailList.find { it.name.equals(className, ignoreCase = true) }
        ?: GymClass(
            name = className,
            schedule = "N/A",
            room = "N/A",
            duration = "N/A",
            availableSlots = 0,
            totalSlots = 0,
            description = "Información no disponible."
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver"
                )
            }
            Text(
                text = "Detalle de clase",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = gymClass.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${gymClass.schedule} · ${gymClass.room} · ${gymClass.duration}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = gymClass.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "${gymClass.availableSlots} de ${gymClass.totalSlots} cupos disponibles",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
