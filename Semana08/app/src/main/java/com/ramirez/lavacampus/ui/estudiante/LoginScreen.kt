package com.ramirez.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.lavacampus.model.Rol
import com.ramirez.lavacampus.ui.components.LavaButton
import com.ramirez.lavacampus.ui.components.LavaTextField
import com.ramirez.lavacampus.viewmodel.SesionViewModel

@Composable
fun LoginScreen(
    vm: SesionViewModel,
    onLoginExito: (String) -> Unit
) {
    var correo by remember { mutableStateOf("ana@correo.test") }
    var clave by remember { mutableStateOf("1234") }
    var rolSeleccionado by remember { mutableStateOf(Rol.CLIENTE.name) }

    val error by vm.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LavaCampus",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Lavandería Universitaria",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Selector de Rol (Estudiante vs Administrador)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterChip(
                selected = rolSeleccionado == Rol.CLIENTE.name,
                onClick = {
                    rolSeleccionado = Rol.CLIENTE.name
                    correo = "ana@correo.test"
                    clave = "1234"
                    vm.limpiarError()
                },
                label = { Text("Estudiante") }
            )

            FilterChip(
                selected = rolSeleccionado == Rol.ADMIN.name,
                onClick = {
                    rolSeleccionado = Rol.ADMIN.name
                    correo = "admin@lavacampus.test"
                    clave = "admin"
                    vm.limpiarError()
                },
                label = { Text("Administrador") }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LavaTextField(
            valor = correo,
            onValorChange = {
                correo = it
                vm.limpiarError()
            },
            label = "Correo institucional",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LavaTextField(
            valor = clave,
            onValorChange = {
                clave = it
                vm.limpiarError()
            },
            label = "Contraseña",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        if (error != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = error!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        LavaButton(
            texto = "Ingresar como " + if (rolSeleccionado == Rol.CLIENTE.name) "Estudiante" else "Admin",
            onClick = {
                vm.login(correo, clave, rolSeleccionado) {
                    onLoginExito(rolSeleccionado)
                }
            }
        )
    }
}