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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DoctorDetailScreen(
    doctorName: String,
    onBackClick: () -> Unit,
    onAppointmentClick: () -> Unit
) {

    val specialty: String
    val experience: String
    val rating: String
    val reviews: String
    val description: String

    when (doctorName) {

        "Dra. Ana Torres" -> {
            specialty = "Cardióloga"
            experience = "12 años exp."
            rating = "4.9"
            reviews = "128 reseñas"
            description =
                "Especialista en arritmias e hipertensión, formación en la Clínica Mayo."
        }

        "Dr. Luis Vega" -> {
            specialty = "Pediatra"
            experience = "8 años exp."
            rating = "4.7"
            reviews = "95 reseñas"
            description =
                "Especialista en atención pediátrica y cuidado integral de niños."
        }

        else -> {
            specialty = "Dermatóloga"
            experience = "10 años exp."
            rating = "4.8"
            reviews = "110 reseñas"
            description =
                "Especialista en dermatología clínica y cuidado de la piel."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        TextButton(
            onClick = onBackClick,
            modifier =
                Modifier.align(Alignment.Start)
        ) {
            Text("←  Volver")
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Surface(
            shape = CircleShape,
            color = MaterialTheme
                .colorScheme.primaryContainer
        ) {

            Text(
                text = "✚",
                style = MaterialTheme
                    .typography.displayMedium,
                color = MaterialTheme
                    .colorScheme.primary,
                modifier = Modifier.padding(24.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = doctorName,
            style = MaterialTheme
                .typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "$specialty · $experience",
            style = MaterialTheme
                .typography.bodyLarge,
            color = MaterialTheme
                .colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "★ $rating  ($reviews)",
            color = MaterialTheme
                .colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme
                    .colorScheme.surfaceVariant
            )
        ) {

            Text(
                text = description,
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme
                    .typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = onAppointmentClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {

            Text(
                text = "Agendar cita",
                fontWeight = FontWeight.Bold
            )
        }
    }
}