package com.ramirez.citas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppointmentScreen(
    onConfirmClick: () -> Unit
) {

    var selectedDate by remember {
        mutableStateOf("")
    }

    var selectedTime by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "← Agendar cita"
        )

        // FECHA
        Text(
            text = "Selecciona fecha",
            modifier = Modifier.padding(top = 24.dp)
        )

        Row {

            FilterChip(
                selected = selectedDate == "Jue 26",

                onClick = {
                    selectedDate = "Jue 26"
                },

                label = {
                    Text("Jue 26")
                },

                modifier = Modifier.padding(end = 8.dp)
            )

            FilterChip(
                selected = selectedDate == "Vie 27",

                onClick = {
                    selectedDate = "Vie 27"
                },

                label = {
                    Text("Vie 27")
                },

                modifier = Modifier.padding(end = 8.dp)
            )

            FilterChip(
                selected = selectedDate == "Sáb 28",

                onClick = {
                    selectedDate = "Sáb 28"
                },

                label = {
                    Text("Sáb 28")
                }
            )
        }

        // HORA
        Text(
            text = "Selecciona hora",
            modifier = Modifier.padding(top = 24.dp)
        )

        Row {

            FilterChip(
                selected = selectedTime == "9:00",

                onClick = {
                    selectedTime = "9:00"
                },

                label = {
                    Text("9:00")
                },

                modifier = Modifier.padding(end = 8.dp)
            )

            FilterChip(
                selected = selectedTime == "10:30",

                onClick = {
                    selectedTime = "10:30"
                },

                label = {
                    Text("10:30")
                },

                modifier = Modifier.padding(end = 8.dp)
            )

            FilterChip(
                selected = selectedTime == "2:00",

                onClick = {
                    selectedTime = "2:00"
                },

                label = {
                    Text("2:00")
                }
            )
        }

        // CONFIRMAR
        Button(
            onClick = onConfirmClick,

            enabled =
                selectedDate.isNotEmpty() &&
                        selectedTime.isNotEmpty(),

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {

            Text(
                text = "Confirmar cita"
            )
        }
    }
}