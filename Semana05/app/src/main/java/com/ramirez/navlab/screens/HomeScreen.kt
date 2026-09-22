package com.ramirez.navlab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramirez.navlab.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    // Degradado vertical: Morado -> Lila -> Lila extremadamente claro/casi blanco
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF6750A4), // Morado
            Color(0xFFE8D8FF), // Lila
            Color(0xFFF7F2FA)  // Lila extremadamente claro / casi blanco
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Bienvenida CENTRADA HORIZONTALMENTE
        Text(
            text = "Bienvenido,\nFabian Ramirez",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Pregunta CENTRADA HORIZONTALMENTE
        Text(
            text = "¿Qué deseas gestionar hoy?",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFFE8D8FF), // Blanco/lila claro
                fontSize = 16.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Tarjeta 1: Directorio de Alumnos
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Screen.List.route) },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Pequeño contenedor lila para el icono
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFFF3E5F5), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = "Alumnos Icon",
                        tint = Color(0xFF6750A4),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Directorio de Alumnos",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Text(
                        text = "Ver y gestionar estudiantes",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta 2: Mi Perfil Académico
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Screen.Profile.route) },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Pequeño contenedor lila para el icono
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFFF3E5F5), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil Icon",
                        tint = Color(0xFF6750A4),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Mi Perfil Académico",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1C1B1F)
                        )
                    )
                    Text(
                        text = "Datos personales y progreso",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Cerrar Sesión Segura en la parte inferior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clickable {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = "Cerrar Sesión",
                tint = Color(0xFFE57373) // Rojo/rosado suave
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cerrar Sesión Segura",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFE57373),
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}
