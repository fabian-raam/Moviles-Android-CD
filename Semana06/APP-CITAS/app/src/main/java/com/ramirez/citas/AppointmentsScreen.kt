package com.ramirez.citas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Appointment(
    val doctor: String,
    val date: String,
    val status: String
)

@Composable
fun AppointmentsScreen() {

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
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Mis citas",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {

            items(appointments) { appointment ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = appointment.doctor
                        )

                        Text(
                            text = appointment.date,
                            modifier =
                                Modifier.padding(top = 4.dp)
                        )

                        Text(
                            text = appointment.status,
                            modifier =
                                Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}