package com.ramirez.navlab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6750A4), // Morado
                        Color(0xFFE8D8FF), // Lila
                        Color(0xFFF7F2FA)  // Lila extremadamente claro
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            
            Text(
                text = "Bienvenido,\nFabian Ramirez",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 40.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "¿Qué deseas gestionar hoy?",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color(0xFFE8D8FF),
                fontSize = 16.sp
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Card 1: Directorio de Alumnos
            HomeCard(
                title = "Directorio de Alumnos",
                subtitle = "Ver y gestionar estudiantes",
                icon = Icons.Default.Group,
                onClick = { navController.navigate(Screen.List.route) }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Card 2: Mi Perfil Académico
            HomeCard(
                title = "Mi Perfil Académico",
                subtitle = "Datos personales y progreso",
                icon = Icons.Default.Person,
                onClick = { navController.navigate(Screen.Profile.route) }
            )
        }
        
        // Cerrar Sesión Segura
        TextButton(
            onClick = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = null,
                tint = Color(0xFFEF5350)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cerrar Sesión Segura",
                color = Color(0xFFEF5350),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun HomeCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = Color(0xFFF3E8FF)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color(0xFF6750A4),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1C1B1F)
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color(0xFF49454F)
                )
            }
        }
    }
}
