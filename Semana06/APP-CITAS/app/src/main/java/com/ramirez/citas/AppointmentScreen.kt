package com.ramirez.citas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AppointmentScreen(
    doctorName: String,
    onBackClick: () -> Unit,
    onConfirmClick: (String, String) -> Unit
) {

    var selectedDate by remember {
        mutableStateOf("")
    }

    var selectedTime by remember {
        mutableStateOf("")
    }

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

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = doctorName,
            style = MaterialTheme
                .typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Selecciona la fecha y hora de tu cita",
            color = MaterialTheme
                .colorScheme.onSurfaceVariant,
            modifier =
                Modifier.padding(top = 4.dp)
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Text(
            text = "Selecciona fecha",
            style = MaterialTheme
                .typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(8.dp),
            modifier =
                Modifier.padding(top = 12.dp)
        ) {

            FilterChip(
                selected =
                    selectedDate == "Jue 26",
                onClick = {
                    selectedDate = "Jue 26"
                },
                label = {
                    Text("Jue 26")
                }
            )

            FilterChip(
                selected =
                    selectedDate == "Vie 27",
                onClick = {
                    selectedDate = "Vie 27"
                },
                label = {
                    Text("Vie 27")
                }
            )

            FilterChip(
                selected =
                    selectedDate == "Sáb 28",
                onClick = {
                    selectedDate = "Sáb 28"
                },
                label = {
                    Text("Sáb 28")
                }
            )
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Text(
            text = "Selecciona hora",
            style = MaterialTheme
                .typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(8.dp),
            modifier =
                Modifier.padding(top = 12.dp)
        ) {

            FilterChip(
                selected =
                    selectedTime == "9:00",
                onClick = {
                    selectedTime = "9:00"
                },
                label = {
                    Text("9:00")
                }
            )

            FilterChip(
                selected =
                    selectedTime == "10:30",
                onClick = {
                    selectedTime = "10:30"
                },
                label = {
                    Text("10:30")
                }
            )

            FilterChip(
                selected =
                    selectedTime == "2:00",
                onClick = {
                    selectedTime = "2:00"
                },
                label = {
                    Text("2:00")
                }
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = {
                onConfirmClick(
                    selectedDate,
                    selectedTime
                )
            },

            enabled =
                selectedDate.isNotEmpty() &&
                        selectedTime.isNotEmpty(),

            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),

            shape =
                RoundedCornerShape(14.dp)
        ) {

            Text(
                text = "Confirmar cita",
                fontWeight = FontWeight.Bold
            )
        }
    }
}