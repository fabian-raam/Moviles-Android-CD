package com.ramirez.citas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
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
        "Cardiología",
        "Pediatría"
    )

    var selectedSpecialty by remember {
        mutableStateOf("")
    }

    val allDoctors = listOf(

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

    val filteredDoctors = if (selectedSpecialty.isEmpty()) {

        allDoctors

    } else {

        allDoctors.filter {
            it.specialty == selectedSpecialty
        }
    }

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet {

                AppDrawer(

                    onHomeClick = onHomeClick,

                    onAppointmentsClick = onAppointmentsClick,

                    onHistoryClick = onHistoryClick,

                    onProfileClick = onProfileClick
                )
            }
        }

    ) {

        Scaffold(

            topBar = {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(

                        onClick = {

                            scope.launch {
                                drawerState.open()
                            }
                        }

                    ) {

                        Text("☰")
                    }

                    Text(
                        text = "Clínica Salud+",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {

                Text(
                    text = "Hola, Juan",
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // FILTROS
                LazyRow(
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {

                    items(specialties) { specialty ->

                        FilterChip(

                            selected =
                                selectedSpecialty == specialty,

                            onClick = {

                                selectedSpecialty =
                                    if (selectedSpecialty == specialty) {

                                        ""

                                    } else {

                                        specialty
                                    }
                            },

                            label = {
                                Text(specialty)
                            },

                            modifier =
                                Modifier.padding(end = 8.dp)
                        )
                    }
                }

                Text(
                    text = "Médicos disponibles",
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // LISTA DE MÉDICOS
                LazyColumn {

                    items(filteredDoctors) { doctor ->

                        Card(

                            onClick = {
                                onDoctorClick(doctor.name)
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)

                        ) {

                            Column(
                                modifier =
                                    Modifier.padding(16.dp)
                            ) {

                                Text(
                                    text = doctor.name
                                )

                                Text(
                                    text = doctor.specialty
                                )

                                Text(
                                    text =
                                        "Valoración: ${doctor.rating} ★"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}