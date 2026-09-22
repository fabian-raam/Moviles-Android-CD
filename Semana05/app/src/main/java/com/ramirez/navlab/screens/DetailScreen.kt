package com.ramirez.navlab.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
                title = { 
                    Text(
                        text = "Expediente Académico",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF7F2FA)
    ) { padding ->

        // Degradado de cabecera exactamente idéntico al de ProfileScreen
        val headerGradient = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF6750A4),
                Color(0xFF9A7FA8),
                Color(0xFFC5B7B9)
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabecera con DEGRADADO HORIZONTAL
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(headerGradient),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Avatar circular grande superpuesto
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .offset(y = 40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8D8FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "FR",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4F378B)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Datos principales centrados
            Text(
                text = "Fabian Ramirez",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1B1F)
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Ingeniería de Software",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFF6750A4),
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card de información con fondo lila/gris muy claro y borde fino visible
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF4EFF6)
                ),
                border = BorderStroke(1.dp, Color(0xFFE0D8E8))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Código (no muestra itemId visualmente)
                    DetailRowItem(icon = Icons.Default.Badge, label = "Código", value = "2024-0001")
                    Spacer(modifier = Modifier.height(16.dp))

                    // Correo
                    DetailRowItem(icon = Icons.Default.Email, label = "Correo", value = "fabian.ramirez@example.com")
                    Spacer(modifier = Modifier.height(16.dp))

                    // Facultad
                    DetailRowItem(icon = Icons.Default.AccountBalance, label = "Facultad", value = "Ingeniería y Tecnología")
                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(color = Color(0xFFE0D8E8))
                    Spacer(modifier = Modifier.height(16.dp))

                    // Biografía
                    Text(
                        text = "Biografía",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF1C1B1F)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Estudiante destacado con interés en desarrollo Android.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF49454F))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DetailRowItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color(0xFF6750A4),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1C1B1F)
                )
            )
        }
    }
}
