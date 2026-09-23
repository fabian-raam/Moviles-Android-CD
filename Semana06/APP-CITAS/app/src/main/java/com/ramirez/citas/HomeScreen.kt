package com.ramirez.citas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class Doctor(
    val name: String,
    val specialty: String,
    val rating: Double
)

@Composable
fun HomeScreen(
    onDoctorClick: (String) -> Unit,
    onHomeClick: () -> Unit,
    onAppointmentsClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    val specialties = listOf(
        "Todos",
        "Cardiología",
        "Pediatría",
        "Dermatología"
    )

    var selectedSpecialty by remember {
        mutableStateOf("Todos")
    }

    val doctors = listOf(
        Doctor(
            "Dra. Ana Torres",
            "Cardiología",
            4.9
        ),
        Doctor(
            "Dr. Luis Vega",
            "Pediatría",
            4.7
        ),
        Doctor(
            "Dra. Rosa Díaz",
            "Dermatología",
            4.8
        )
    )

    val filteredDoctors =
        if (selectedSpecialty == "Todos" || selectedSpecialty.isEmpty()) {
            doctors
        } else {
            doctors.filter {
                it.specialty == selectedSpecialty
            }
        }

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet {

                AppDrawer(
                    onHomeClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onHomeClick()
                    },

                    onAppointmentsClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onAppointmentsClick()
                    },

                    onHistoryClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onHistoryClick()
                    },

                    onProfileClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onProfileClick()
                    }
                )
            }
        }
    ) {

        Scaffold(

            topBar = {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Text(
                            text = "☰",
                            style = MaterialTheme
                                .typography.titleLarge
                        )
                    }

                    Text(
                        text = "Clínica Salud+",
                        style = MaterialTheme
                            .typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme
                            .colorScheme.primary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {

                Text(
                    text = "Hola, Juan",
                    style = MaterialTheme
                        .typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        top = 12.dp,
                        bottom = 16.dp
                    )
                )

                LazyRow(
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp),
                    modifier =
                        Modifier.padding(bottom = 24.dp)
                ) {

                    items(specialties) { specialty ->

                        FilterChip(
                            selected =
                                selectedSpecialty == specialty ||
                                        (specialty == "Todos" && selectedSpecialty.isEmpty()),

                            onClick = {

                                selectedSpecialty =
                                    if (
                                        selectedSpecialty == specialty ||
                                        specialty == "Todos"
                                    ) {
                                        "Todos"
                                    } else {
                                        specialty
                                    }
                            },

                            label = {
                                Text(specialty)
                            }
                        )
                    }
                }

                Text(
                    text = "Médicos disponibles",
                    style = MaterialTheme
                        .typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier =
                        Modifier.padding(bottom = 12.dp)
                )

                LazyColumn(
                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    items(filteredDoctors) { doctor ->

                        Card(
                            onClick = {
                                onDoctorClick(doctor.name)
                            },

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

                                Text(
                                    text = "✚",
                                    style = MaterialTheme
                                        .typography.headlineMedium,
                                    color = MaterialTheme
                                        .colorScheme.primary,
                                    modifier =
                                        Modifier.padding(end = 16.dp)
                                )

                                Column(
                                    modifier =
                                        Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = doctor.name,
                                        style = MaterialTheme
                                            .typography.titleMedium,
                                        fontWeight =
                                            FontWeight.Bold
                                    )

                                    Text(
                                        text = doctor.specialty,
                                        style = MaterialTheme
                                            .typography.bodyMedium,
                                        color = MaterialTheme
                                            .colorScheme
                                            .onSurfaceVariant
                                    )
                                }

                                Text(
                                    text = "★ ${doctor.rating}",
                                    color = MaterialTheme
                                        .colorScheme.primary,
                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}