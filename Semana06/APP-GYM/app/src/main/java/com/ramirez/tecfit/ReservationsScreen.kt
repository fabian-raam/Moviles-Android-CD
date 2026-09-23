package com.ramirez.tecfit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ReservationsScreen(
    reservations: List<Reservation>
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Mis reservas",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (reservations.isEmpty()) {

            Text(
                text = "Aún no tienes reservas.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )

        } else {

            LazyColumn(
                contentPadding = PaddingValues(
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(reservations) { reservation ->

                    ReservationCard(
                        reservation = reservation
                    )
                }
            }
        }
    }
}

@Composable
fun ReservationCard(
    reservation: Reservation
) {

    val isConfirmed =
        reservation.status.equals(
            "Confirmada",
            ignoreCase = true
        )

    val statusBgColor =
        if (isConfirmed) {
            Color(0xFFE8F5E9)
        } else {
            Color(0xFFEEEEEE)
        }

    val statusTextColor =
        if (isConfirmed) {
            Color(0xFF2E7D32)
        } else {
            Color(0xFF616161)
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = reservation.className,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = reservation.schedule,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF666666)
                )
            }

            Surface(
                color = statusBgColor,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(
                    start = 8.dp
                )
            ) {

                Text(
                    text = reservation.status,
                    color = statusTextColor,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 6.dp
                    )
                )
            }
        }
    }
}