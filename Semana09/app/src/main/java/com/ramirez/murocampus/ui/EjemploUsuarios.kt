package com.ramirez.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramirez.murocampus.viewmodel.UiState
import com.ramirez.murocampus.viewmodel.UsuariosViewModel

// Ejemplo completo de observación de estado. No se muestra en la pantalla inicial.
@Composable
fun EjemploUsuarios(vm: UsuariosViewModel) {
    val estado by vm.usuarios.collectAsStateWithLifecycle()
    when (val actual = estado) {
        UiState.Cargando -> Cargando(mensaje = "Cargando usuarios…")
        is UiState.Error -> ErrorConReintento(actual.mensaje, vm::cargar)
        is UiState.Exito -> LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(actual.datos, key = { it.id }) { usuario ->
                Text("${usuario.name}\n${usuario.email}")
            }
        }
    }
}
