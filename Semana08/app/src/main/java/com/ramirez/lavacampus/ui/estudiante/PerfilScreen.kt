package com.ramirez.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.lavacampus.data.local.entity.Usuario
import com.ramirez.lavacampus.model.Rol
import com.ramirez.lavacampus.ui.components.AppTopBar
import com.ramirez.lavacampus.ui.components.LavaButton
import com.ramirez.lavacampus.viewmodel.SesionViewModel

@Composable
fun PerfilScreen(
    usuario: Usuario,
    vm: SesionViewModel,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val modoOscuro by vm.modoOscuro.collectAsState()

    Scaffold(
        topBar = { AppTopBar(titulo = "Mi Perfil") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = usuario.nombre, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Código: ${usuario.codigo}", fontSize = 14.sp)
                    Text(text = "Correo: ${usuario.correo}", fontSize = 14.sp)
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Modo Oscuro")
                    Switch(
                        checked = modoOscuro,
                        onCheckedChange = { }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            LavaButton(
                texto = "Cambiar a Perfil Administrador",
                onClick = {
                    vm.login("admin@lavacampus.test", "admin", Rol.ADMIN.name) {
                        onCambiarPerfil()
                    }
                }
            )

            OutlinedButton(
                onClick = {
                    vm.cerrarSesion()
                    onCerrarSesion()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cerrar Sesión", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}