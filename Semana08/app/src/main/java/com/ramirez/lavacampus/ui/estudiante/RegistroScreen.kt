package com.ramirez.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.lavacampus.ui.components.AppTopBar
import com.ramirez.lavacampus.ui.components.LavaButton
import com.ramirez.lavacampus.ui.components.LavaTextField
import com.ramirez.lavacampus.ui.theme.TurquesaPrimary
import com.ramirez.lavacampus.viewmodel.RegistroViewModel

@Composable
fun RegistroScreen(
    vm: RegistroViewModel,
    onRegistroExito: () -> Unit,
    onVolver: () -> Unit
) {
    val nombre by vm.nombre.collectAsState()
    val codigo by vm.codigo.collectAsState()
    val correo by vm.correo.collectAsState()
    val clave by vm.clave.collectAsState()
    val confirmarClave by vm.confirmarClave.collectAsState()
    val error by vm.error.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Registro de Estudiante", onVolver = onVolver) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Crea tu cuenta de estudiante",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TurquesaPrimary
                )
                Text(
                    text = "Solo disponible para alumnos del campus",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            item {
                LavaTextField(
                    valor = nombre,
                    onValorChange = { vm.cambiarNombre(it) },
                    label = "Juan Pérez"
                )
            }

            item {
                LavaTextField(
                    valor = codigo,
                    onValorChange = { vm.cambiarCodigo(it) },
                    label = "202310",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            item {
                LavaTextField(
                    valor = correo,
                    onValorChange = { vm.cambiarCorreo(it) },
                    label = "alumno@correo.com",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }

            item {
                LavaTextField(
                    valor = clave,
                    onValorChange = { vm.cambiarClave(it) },
                    label = "Contraseña (mín. 5 dígitos)",
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }

            item {
                LavaTextField(
                    valor = confirmarClave,
                    onValorChange = { vm.cambiarConfirmarClave(it) },
                    label = "Confirmar contraseña",
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }

            if (error != null) {
                item {
                    Text(
                        text = error!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                LavaButton(
                    texto = "Crear Cuenta",
                    containerColor = TurquesaPrimary,
                    onClick = {
                        vm.registrar {
                            onRegistroExito()
                        }
                    }
                )
            }

            item {
                TextButton(onClick = onVolver) {
                    Text("¿Ya tienes cuenta? Inicia sesión", color = TurquesaPrimary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
