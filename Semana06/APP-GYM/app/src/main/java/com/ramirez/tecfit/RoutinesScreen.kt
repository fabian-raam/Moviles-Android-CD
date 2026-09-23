package com.ramirez.tecfit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RoutinesScreen(
    reservations: List<Reservation>
) {

    val greenColor = Color(0xFF2E7D32)
    val lightGreenColor = Color(0xFFE8F5E9)
    val backgroundColor = Color(0xFFF7F8F7)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        Column(
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
                top = 20.dp
            )
        ) {

            Text(
                text = "Mis rutinas",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Rutinas según tus clases reservadas",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF666666)
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (reservations.isEmpty()) {

            Text(
                text = "Aún no tienes clases reservadas.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(20.dp)
            )

        } else {

            LazyColumn(
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(reservations) { reservation ->

                    RoutineCard(
                        reservation = reservation,
                        greenColor = greenColor,
                        lightGreenColor = lightGreenColor
                    )
                }
            }
        }
    }
}

@Composable
fun RoutineCard(
    reservation: Reservation,
    greenColor: Color,
    lightGreenColor: Color
) {

    val exercises = when (reservation.className) {

        "Cross Training" -> listOf(
            "Sentadillas - 3 series",
            "Flexiones - 3 series",
            "Burpees - 10 repeticiones"
        )

        "Yoga funcional" -> listOf(
            "Movilidad articular - 5 min",
            "Plancha - 3 series",
            "Estiramientos - 10 min"
        )

        "Spinning" -> listOf(
            "Calentamiento - 5 min",
            "Intervalos intensos - 15 min",
            "Pedaleo moderado - 10 min"
        )

        else -> listOf(
            "Rutina asignada según la clase"
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = lightGreenColor,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Rutina",
                        tint = greenColor
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {

                    Text(
                        text = reservation.className,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = reservation.schedule,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF666666)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Text(
                text = "Rutina",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = greenColor
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            exercises.forEach { exercise ->

                Text(
                    text = "• $exercise",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(
                        vertical = 3.dp
                    )
                )
            }
        }
    }
}