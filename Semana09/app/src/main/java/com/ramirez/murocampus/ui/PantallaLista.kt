package com.ramirez.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
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
fun PantallaLista(
    vm: PostsViewModel,
    onAbrir: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val estado by vm.lista.collectAsStateWithLifecycle()
    val usuarioFiltro by vm.usuarioFiltro.collectAsStateWithLifecycle()

    Column(modifier.fillMaxSize()) {
        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item(key = "todos") {
                FilterChip(
                    selected = usuarioFiltro == null,
                    onClick = { vm.filtrar(null) },
                    label = { Text("Todos") }
                )
            }
            items((1..10).toList(), key = { it }) { userId ->
                FilterChip(
                    selected = usuarioFiltro == userId,
                    onClick = { vm.filtrar(userId) },
                    label = { Text("Usuario $userId") }
                )
            }
        }
        Box(Modifier.weight(1f)) {
            when (val actual = estado) {
                UiState.Cargando -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Cargando()
                }
                is UiState.Error -> Box(Modifier.fillMaxSize().padding(16.dp)) {
                    ErrorConReintento(mensaje = actual.mensaje, onReintentar = vm::cargar)
                }
                is UiState.Exito -> if (actual.datos.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Sin publicaciones")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(actual.datos, key = { it.id }) { post ->
                            TarjetaPost(
                                userId = post.userId,
                                titulo = post.title,
                                texto = post.body,
                                onClick = { onAbrir(post.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
