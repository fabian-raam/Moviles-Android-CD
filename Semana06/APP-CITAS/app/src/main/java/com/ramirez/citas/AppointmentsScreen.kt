package com.ramirez.citas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class Appointment(
    val doctor: String,
    val date: String,
    val status: String
)

@Composable
fun AppointmentsScreen(
    onBackClick: () -> Unit
) {

    val appointments = listOf(

        Appointment(
            doctor = "Dra. Ana Torres",
            date = "Viernes 27, 10:30 am",
            status = "Confirmada"
        ),

        Appointment(
            doctor = "Dr. Luis Vega",
            date = "Miércoles 15, 3:00 pm",
            status = "Completada"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        TextButton(
            onClick = onBackClick
        ) {
            Text("←  Volver")
        }

        Text(
            text = "Mis citas",
            style = MaterialTheme
                .typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier =
                Modifier.padding(vertical = 16.dp)
        )

        LazyColumn(
            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            items(appointments) { appointment ->

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(18.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme
                                    .colorScheme
                                    .surfaceVariant
                        )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Column(
                            modifier =
                                Modifier.weight(1f)
                        ) {

                            Text(
                                text =
                                    appointment.doctor,
                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,
                                fontWeight =
                                    FontWeight.Bold
                            )

                            Text(
                                text =
                                    appointment.date,
                                modifier =
                                    Modifier.padding(
                                        top = 6.dp
                                    ),
                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant
                            )
                        }

                        Text(
                            text =
                                appointment.status,
                            color =
                                if (
                                    appointment.status ==
                                    "Confirmada"
                                ) {
                                    MaterialTheme
                                        .colorScheme.primary
                                } else {
                                    MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant
                                },
                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}