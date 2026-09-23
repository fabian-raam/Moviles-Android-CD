package com.ramirez.citas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmationScreen(
    doctorName: String,
    date: String,
    time: String,
    onAppointmentsClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Surface(
            shape = CircleShape,
            color = MaterialTheme
                .colorScheme.primaryContainer
        ) {

            Text(
                text = "✓",
                style = MaterialTheme
                    .typography.displayMedium,
                color = MaterialTheme
                    .colorScheme.primary,
                modifier = Modifier.padding(20.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "¡Cita agendada!",
            style = MaterialTheme
                .typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = doctorName,
            style = MaterialTheme
                .typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "$date, $time",
            style = MaterialTheme
                .typography.bodyLarge,
            color = MaterialTheme
                .colorScheme.onSurfaceVariant,
            modifier =
                Modifier.padding(top = 6.dp)
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = onAppointmentsClick,

            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),

            shape =
                RoundedCornerShape(14.dp)
        ) {

            Text(
                text = "Ver mis citas",
                fontWeight = FontWeight.Bold
            )
        }
    }
}