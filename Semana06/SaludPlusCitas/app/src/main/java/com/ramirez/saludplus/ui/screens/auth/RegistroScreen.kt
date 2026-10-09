package com.ramirez.saludplus.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ramirez.saludplus.data.model.Usuario
import com.ramirez.saludplus.data.repository.Repositorio
import com.ramirez.saludplus.navigation.Rutas

@Composable
fun RegistroScreen(navController: NavHostController) {

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    var mensajeError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crear cuenta",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Regístrate para agendar tus citas",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Campos de texto agrupados en un Column
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Nombre completo
            Text(
                text = "Nombre completo",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                placeholder = { Text("Juan Pérez") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Nombre")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Teléfono
            Text(
                text = "Teléfono",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                placeholder = { Text("987 654 321") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Teléfono")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Correo
            Text(
                text = "Correo",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                placeholder = { Text("juan@correo.com") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Correo")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Contraseña
            Text(
                text = "Contraseña",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                placeholder = { Text("••••••••") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Contraseña")
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Botón y pie de página
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Mensaje de error condicional con margen inferior para separarlo del botón
            if (mensajeError.isNotBlank()) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            Button(
                onClick = {
                    // 1. Validar que no haya campos vacíos
                    if (nombre.isBlank() || telefono.isBlank() || correo.isBlank() || contrasena.isBlank()) {
                        mensajeError = "Por favor, completa todos los campos"
                        return@Button
                    }

                    // 2. Validar nombre: cada palabra debe iniciar con mayúscula y el resto en minúsculas
                    val palabras = nombre.trim().split("\\s+".toRegex())
                    val nombreValido = palabras.all { palabra ->
                        palabra.isNotEmpty() && palabra[0].isUpperCase() && palabra.drop(1).all { it.isLowerCase() }
                    }
                    if (!nombreValido) {
                        mensajeError = "Cada palabra debe iniciar con mayúscula y el resto minúsculas"
                        return@Button
                    }

                    // 3. Validar teléfono: solo números y exactamente 9 caracteres
                    if (!telefono.all { it.isDigit() } || telefono.length != 9) {
                        mensajeError = "El teléfono debe contener exactamente 9 dígitos"
                        return@Button
                    }

                    // 4. Validar contraseña: al menos 5 caracteres
                    if (contrasena.length < 5) {
                        mensajeError = "La contraseña debe tener al menos 5 caracteres"
                        return@Button
                    }

                    // 5. Validar formato básico de correo
                    if (!correo.contains("@") || !correo.contains(".")) {
                        mensajeError = "Ingresa un correo electrónico válido"
                        return@Button
                    }

                    // 6. Crear usuario e intentar registrarlo en el Repositorio
                    val nuevoUsuario = Usuario(
                        id = Repositorio.usuarios.size + 1,
                        nombre = nombre,
                        telefono = telefono,
                        correo = correo,
                        contrasena = contrasena
                    )

                    val exito = Repositorio.registrarUsuario(nuevoUsuario)

                    if (exito) {
                        mensajeError = ""
                        // Navegar al Home y limpiar el historial de registro
                        navController.navigate(Rutas.HOME) {
                            popUpTo(Rutas.REGISTRO) { inclusive = true }
                        }
                    } else {
                        mensajeError = "El correo ya se encuentra registrado"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Registrarme",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Al registrarte aceptas nuestros",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            TextButton(
                onClick = {
                    navController.navigate(Rutas.TERMINOS)
                }
            ) {
                Text(
                    text = "Términos y Condiciones",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Ya tienes una cuenta?",
                    fontSize = 14.sp
                )
                TextButton(
                    onClick = {
                        navController.navigate(Rutas.LOGIN)
                    }
                ) {
                    Text(
                        text = "Iniciar sesión",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
