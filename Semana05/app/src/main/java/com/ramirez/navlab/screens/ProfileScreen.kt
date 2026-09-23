package com.ramirez.navlab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                title = { Text("Configuración de Perfil") },
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Cabecera Profile con degradado horizontal
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF6750A4),
                                    Color(0xFF9A7FA8),
                                    Color(0xFFC5B7B9)
                                )
                            )
                        )
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(80.dp),
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.2f)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(4.dp)
                                .background(Color.White, CircleShape)
                        ) {
                            Text(
                                text = "FR",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6750A4)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "Fabian Ramirez",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Datos de Información Personal y Académico
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = "Información Personal",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6750A4)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileRow(label = "Nombre Completo", value = "Fabian Ramirez")
                    ProfileRow(label = "Correo", value = "fabian.ramirez@tecsup.edu.pe")
                    ProfileRow(label = "Teléfono", value = "+51 987 654 321")

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = Color(0xFFE0D8E8))
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Información Académica",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6750A4)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileRow(label = "Carrera", value = "Ingeniería de Software")
                    ProfileRow(label = "Ciclo Actual", value = "VI Ciclo")
                }
            }

            // Botón Cerrar Sesión anclado a la parte inferior
            Button(
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(start = 20.dp, end = 20.dp, bottom = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Cerrar Sesión",
                    tint = Color(0xFFEF5350)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cerrar Sesión",
                    color = Color(0xFFEF5350),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ProfileRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(text = label, fontSize = 12.sp, color = Color(0xFF49454F))
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1C1B1F))
    }
}
