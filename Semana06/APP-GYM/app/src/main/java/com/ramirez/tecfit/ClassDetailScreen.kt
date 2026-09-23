package com.ramirez.tecfit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    onBackClick: () -> Unit = {},
    onReserveClick: (String, String, String) -> Unit = { _, _, _ -> }
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

    val greenColor = Color(0xFF2E7D32)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
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
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { onReserveClick(gymClass.name, gymClass.schedule, gymClass.room) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = greenColor),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Reservar cupo",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(greenColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Entrenamiento",
                        tint = greenColor,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "TECSUP FIT - ${gymClass.room}",
                        style = MaterialTheme.typography.labelLarge,
                        color = greenColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = gymClass.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${gymClass.schedule} · ${gymClass.room} · ${gymClass.duration}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Descripción",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = gymClass.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = greenColor.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Cupos",
                        tint = greenColor
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Column {
                        Text(
                            text = "Cupos disponibles",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${gymClass.availableSlots} de ${gymClass.totalSlots} cupos disponibles",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = greenColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
