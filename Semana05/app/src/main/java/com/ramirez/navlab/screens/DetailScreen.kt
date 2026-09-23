package com.ramirez.navlab.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.School
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    itemId: Int
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expediente Académico") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Cabecera con degradado horizontal
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF6750A4),
                                Color(0xFF9A7FA8),
                                Color(0xFFC5B7B9)
                            )
                        )
                    )
            )

            // Contenedor para el Avatar superpuesto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-50).dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(4.dp)
                            .background(Color(0xFFE8D8FF), CircleShape)
                    ) {
                        Text(
                            text = "FR",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6750A4)
                        )
                    }
                }
            }

            // Datos principales centrados
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-40).dp)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Fabian Ramirez",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFF1C1B1F)
                )
                Text(
                    text = "Ingeniería de Software",
                    fontSize = 16.sp,
                    color = Color(0xFF6750A4)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Card de información
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF4EFF6)),
                    border = BorderStroke(1.dp, Color(0xFFE0D8E8))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        InfoRow(icon = Icons.Default.Badge, label = "Código", value = "2024-0001")
                        Spacer(modifier = Modifier.height(16.dp))
                        InfoRow(icon = Icons.Default.Email, label = "Correo", value = "fabian.ramirez@example.com")
                        Spacer(modifier = Modifier.height(16.dp))
                        InfoRow(icon = Icons.Default.School, label = "Facultad", value = "Ingeniería y Tecnología")

                        Spacer(modifier = Modifier.height(20.dp))
                        HorizontalDivider(color = Color(0xFFE0D8E8))
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Biografía",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF1C1B1F)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Estudiante destacado con interés en desarrollo Android.",
                            fontSize = 14.sp,
                            color = Color(0xFF49454F)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF6750A4),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = Color(0xFF49454F))
            Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1C1B1F))
        }
    }
}
