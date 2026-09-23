package com.ramirez.navlab.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramirez.navlab.navigation.Screen

data class Alumno(val nombre: String, val carrera: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController) {

    val alumnos = listOf(
        Alumno("Fabian Ramirez", "Ingeniería de Software"),
        Alumno("María García", "Arquitectura"),
        Alumno("Carlos Pérez", "Medicina"),
        Alumno("Ana López", "Derecho"),
        Alumno("Luis Ramírez", "Administración")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Directorio de Alumnos",
                        color = Color(0xFF4F378B),
                        fontWeight = FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color(0xFF4F378B)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE8D8FF)
                )
            )
        }
    ) { padding ->

        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            itemsIndexed(alumnos) { index, alumno ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {
                            navController.navigate(
                                Screen.Detail.createRoute(index + 1)
                            )
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE4E0E6))
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar circular con inicial
                        Surface(
                            modifier = Modifier.size(44.dp),
                            shape = CircleShape,
                            color = Color(0xFF6750A4)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = alumno.nombre.take(1),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Column con nombre y carrera
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = alumno.nombre,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF1C1B1F)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = alumno.carrera,
                                fontSize = 14.sp,
                                color = Color(0xFF6750A4)
                            )
                        }

                        // Flecha derecha
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
