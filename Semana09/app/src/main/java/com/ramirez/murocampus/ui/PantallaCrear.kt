package com.ramirez.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramirez.murocampus.viewmodel.PostsViewModel
import com.ramirez.murocampus.viewmodel.UiState

@Composable
fun PantallaCrear(vm: PostsViewModel, modifier: Modifier = Modifier) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var texto by rememberSaveable { mutableStateOf("") }
    val estado by vm.publicacion.collectAsStateWithLifecycle()
    val publicando = estado == UiState.Cargando

    LaunchedEffect(estado) {
        if (estado is UiState.Exito) {
            titulo = ""
            texto = ""
        }
    }

    Column(
        modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Crear publicación", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it; vm.limpiarResultadoPublicacion() },
            label = { Text("Título") },
            singleLine = true,
            enabled = !publicando,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it; vm.limpiarResultadoPublicacion() },
            label = { Text("Contenido") },
            minLines = 4,
            maxLines = 8,
            enabled = !publicando,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { vm.publicar(titulo, texto) },
            enabled = titulo.isNotBlank() && texto.isNotBlank() && !publicando,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Publicar") }

        when (val actual = estado) {
            null -> Unit
            UiState.Cargando -> Cargando(mensaje = "Publicando…")
            is UiState.Exito -> Text(
                "Publicado con id ${actual.datos.id}",
                color = MaterialTheme.colorScheme.primary
            )
            is UiState.Error -> ErrorConReintento(
                mensaje = actual.mensaje,
                onReintentar = { vm.publicar(titulo, texto) }
            )
        }
    }
}
