package com.ramirez.citas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Doctor(val name: String, val specialty: String, val rating: Double)

@Composable
fun HomeScreen(onDoctorClick: () -> Unit) {
    val specialties = listOf("Cardiología", "Pediatría")
    var selectedSpecialty by remember { mutableStateOf("") }

    val allDoctors = listOf(
        Doctor("Dra. Ana Torres", "Cardiología", 4.9),
        Doctor("Dr. Luis Vega", "Pediatría", 4.7),
        Doctor("Dra. Rosa Díaz", "Dermatología", 4.8)
    )

    val filteredDoctors = if (selectedSpecialty.isEmpty()) {
        allDoctors
    } else {
        allDoctors.filter { it.specialty == selectedSpecialty }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Clínica Salud+")
        Text(text = "Hola, Juan", modifier = Modifier.padding(vertical = 8.dp))

        LazyRow(modifier = Modifier.padding(bottom = 16.dp)) {
            items(specialties) { specialty ->
                FilterChip(
                    selected = selectedSpecialty == specialty,
                    onClick = {
                        selectedSpecialty = if (selectedSpecialty == specialty) "" else specialty
                    },
                    label = { Text(text = specialty) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Text(text = "Médicos disponibles", modifier = Modifier.padding(bottom = 8.dp))

        LazyColumn {
            items(filteredDoctors) { doctor ->
                Card(
                    onClick = onDoctorClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = doctor.name)
                        Text(text = doctor.specialty)
                        Text(text = "Valoración: ${doctor.rating} ★")
                    }
                }
            }
        }
    }
}
