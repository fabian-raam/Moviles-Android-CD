package com.ramirez.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramirez.murocampus.viewmodel.PostsViewModel
import com.ramirez.murocampus.viewmodel.UiState

@Composable
fun PantallaDetalle(vm: PostsViewModel, id: Int, modifier: Modifier = Modifier) {
    val estado by vm.detalle.collectAsStateWithLifecycle()
    val eliminacion by vm.eliminacion.collectAsStateWithLifecycle()

    when (val actual = estado) {
        UiState.Cargando -> Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Cargando(mensaje = "Cargando publicación…")
        }
        is UiState.Error -> Box(modifier.fillMaxSize().padding(16.dp)) {
            ErrorConReintento(actual.mensaje, onReintentar = { vm.abrir(id) })
        }
        is UiState.Exito -> LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(actual.datos.post.title, style = MaterialTheme.typography.headlineSmall)
            }
            item { Text(actual.datos.post.body) }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { vm.eliminar(id) },
                        enabled = eliminacion != UiState.Cargando && eliminacion !is UiState.Exito
                    ) {
                        Text(if (eliminacion == UiState.Cargando) "Eliminando…" else "Eliminar")
                    }
                    when (val resultado = eliminacion) {
                        null, UiState.Cargando -> Unit
                        is UiState.Exito -> Text(
                            "Publicación eliminada",
                            color = MaterialTheme.colorScheme.primary
                        )
                        is UiState.Error -> ErrorConReintento(
                            resultado.mensaje,
                            onReintentar = { vm.eliminar(id) }
                        )
                    }
                }
            }
            item { HorizontalDivider() }
            item {
                Text(
                    "Comentarios (${actual.datos.comentarios.size})",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(actual.datos.comentarios, key = { it.id }) { comentario ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(comentario.email, color = MaterialTheme.colorScheme.primary)
                    Text(comentario.body)
                    HorizontalDivider()
                }
            }
        }
    }
}
