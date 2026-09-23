package com.ramirez.citas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmationScreen(
    onAppointmentsClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text = "✓"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "¡Cita agendada!"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Dra. Ana Torres"
        )

        Text(
            text = "Viernes 27, 10:30 am"
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = onAppointmentsClick,

            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Ver mis citas"
            )
        }
    }
}