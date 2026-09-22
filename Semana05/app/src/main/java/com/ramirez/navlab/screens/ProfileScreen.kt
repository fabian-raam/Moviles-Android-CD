package com.ramirez.navlab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramirez.navlab.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Configuración de Perfil",
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

        // Estructura de Box ocupando todo el espacio disponible para ANCLAR el botón abajo de forma limpia
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Contenido escroleable principal alineado arriba
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Cabecera con degradado horizontal morado -> rosado/griscálido
                val headerGradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF6750A4),
                        Color(0xFF9A7FA8),
                        Color(0xFFC5B7B9)
                    )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(headerGradient)
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar circular placeholder
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar Profile",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Nombre blanco y destacado
                    Text(
                        text = "Fabian Ramirez",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bloques de Información
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    // INFORMACIÓN PERSONAL
                    Text(
                        text = "INFORMACIÓN PERSONAL",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6750A4),
                            letterSpacing = 1.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileRowItem(icon = Icons.Default.Person, label = "Nombre Completo", value = "Fabian Ramirez")
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileRowItem(icon = Icons.Default.Email, label = "Correo", value = "fabian.ramirez@tecsup.edu.pe")
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileRowItem(icon = Icons.Default.Phone, label = "Teléfono", value = "+51 987 654 321")

                    Spacer(modifier = Modifier.height(32.dp))

                    // ACADÉMICO
                    Text(
                        text = "ACADÉMICO",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6750A4),
                            letterSpacing = 1.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileRowItem(icon = Icons.Default.School, label = "Carrera", value = "Ingeniería de Software")
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileRowItem(icon = Icons.Default.Book, label = "Ciclo Actual", value = "VI Ciclo")
                }

                // Margen extra inferior para que el scroll libre no choque con el botón anclado
                Spacer(modifier = Modifier.height(100.dp))
            }

            // BOTÓN CERRAR SESIÓN ANCLADO COMPLETAMENTE ABAJO CON Alignment.BottomCenter
            Button(
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFEBEE), // Rosado extremadamente claro
                    contentColor = Color(0xFFEF5350)     // Contenido rojo/rosado
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Exit Icon",
                        tint = Color(0xFFEF5350)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Cerrar Sesión",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileRowItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
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
