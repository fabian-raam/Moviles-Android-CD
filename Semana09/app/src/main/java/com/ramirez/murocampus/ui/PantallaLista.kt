package com.ramirez.murocampus.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramirez.murocampus.ui.theme.*
import com.ramirez.murocampus.viewmodel.PostsViewModel
import com.ramirez.murocampus.viewmodel.UiState

@Composable
private fun FiltroUsuario(texto: String, seleccionado: Boolean, onClick: () -> Unit) {
    Box(
        Modifier.heightIn(min = 48.dp).padding(3.dp)
            .then(if (seleccionado) Modifier.shadow(3.dp, RoundedCornerShape(9.dp)) else Modifier)
            .clip(RoundedCornerShape(9.dp))
            .background(if (seleccionado) Blanco else Segmento)
            .clickable(onClick = onClick)
            .semantics { contentDescription = if (seleccionado) "$texto, seleccionado" else texto }
            .padding(horizontal = 18.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) { Text(texto, style = MaterialTheme.typography.labelMedium, color = TextoPrincipal) }
}

@Composable
fun PantallaLista(vm: PostsViewModel, onAbrir: (Int) -> Unit, modifier: Modifier = Modifier) {
    val estado by vm.lista.collectAsStateWithLifecycle()
    val usuarioFiltro by vm.usuarioFiltro.collectAsStateWithLifecycle()
    val busqueda by vm.busqueda.collectAsStateWithLifecycle()
    val cantidadDescargada by vm.cantidadDescargada.collectAsStateWithLifecycle()
    val hayMas by vm.hayMas.collectAsStateWithLifecycle()
    val cargaMas by vm.cargaMas.collectAsStateWithLifecycle()

    Column(modifier.fillMaxSize()) {
        LazyRow(
            modifier = Modifier.padding(horizontal = 20.dp).clip(RoundedCornerShape(12.dp)).background(Segmento),
            contentPadding = PaddingValues(2.dp)
        ) {
            item(key = "todos") { FiltroUsuario("Todos", usuarioFiltro == null) { vm.filtrar(null) } }
            items((1..10).toList(), key = { it }) { id ->
                FiltroUsuario("Usuario $id", usuarioFiltro == id) { vm.filtrar(id) }
            }
        }
        Spacer(Modifier.height(12.dp))
        Surface(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            shape = RoundedCornerShape(14.dp), color = Blanco
        ) {
            Row(Modifier.heightIn(min = 48.dp).padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconoBuscar(Modifier.size(20.dp), TextoTenue)
                Spacer(Modifier.width(8.dp))
                BasicTextField(
                    value = busqueda, onValueChange = vm::buscar,
                    modifier = Modifier.weight(1f).padding(vertical = 12.dp).semantics { contentDescription = "Buscar por título" },
                    singleLine = true, cursorBrush = SolidColor(Coral),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextoPrincipal),
                    decorationBox = { campo ->
                        Box {
                            if (busqueda.isEmpty()) Text("Buscar por título", style = MaterialTheme.typography.bodyMedium, color = TextoTenue)
                            campo()
                        }
                    }
                )
                if (busqueda.isNotEmpty()) {
                    TextButton(onClick = { vm.buscar("") }, contentPadding = PaddingValues(horizontal = 8.dp)) {
                        Text("Limpiar", style = MaterialTheme.typography.labelMedium, color = Coral)
                    }
                }
            }
        }
        if (estado is UiState.Exito) {
            Text("$cantidadDescargada publicaciones", color = TextoTenue, style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 22.dp, top = 12.dp, bottom = 2.dp))
        }
        Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            when (val actual = estado) {
                UiState.Cargando -> Cargando()
                is UiState.Error -> ErrorConReintento(actual.mensaje, vm::cargar)
                is UiState.Exito -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (actual.datos.isEmpty()) {
                        item(key = "vacio") {
                            Text(if (busqueda.isBlank()) "Sin publicaciones" else "Sin coincidencias entre las publicaciones cargadas",
                                modifier = Modifier.padding(vertical = 24.dp), color = TextoSecundario)
                        }
                    }
                    items(actual.datos, key = { it.id }) { post ->
                        TarjetaPost(post.userId, post.title, post.body, onClick = { onAbrir(post.id) })
                    }
                    item(key = "paginacion") {
                        when (val siguiente = cargaMas) {
                            UiState.Cargando -> Cargando(mensaje = "Cargando más publicaciones…")
                            is UiState.Error -> ErrorConReintento(siguiente.mensaje, vm::cargarMas, compacto = true)
                            else -> if (hayMas) {
                                BotonCoral("Cargar más", vm::cargarMas, Modifier.fillMaxWidth())
                            } else {
                                Text("No hay más publicaciones", color = TextoTenue, style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(vertical = 12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
