package com.ramirez.citas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        modifier = Modifier.padding(16.dp)
    ) {

        Button(
            onClick = onBackClick
        ) {
            Text("← Perfil del médico")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = doctorName)

        Text(
            text = "$specialty · $experience"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "★ $rating ($reviews)"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = description
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAppointmentClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agendar cita")
        }
    }
}