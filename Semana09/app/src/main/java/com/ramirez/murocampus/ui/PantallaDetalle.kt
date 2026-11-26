package com.ramirez.murocampus.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramirez.murocampus.ui.theme.*
import com.ramirez.murocampus.viewmodel.PostsViewModel
import com.ramirez.murocampus.viewmodel.UiState

@Composable
fun PantallaDetalle(vm: PostsViewModel, id: Int, modifier: Modifier = Modifier) {
    val estado by vm.detalle.collectAsStateWithLifecycle()
    val eliminacion by vm.eliminacion.collectAsStateWithLifecycle()

    when (val actual = estado) {
        UiState.Cargando -> Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Cargando(mensaje = "Cargando publicación…") }
        is UiState.Error -> Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ErrorConReintento(actual.mensaje, onReintentar = { vm.abrir(id) })
        }
        is UiState.Exito -> LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AvatarUsuario("U${actual.datos.post.userId}", colorUsuario(actual.datos.post.userId))
                    Column {
                        Text("Usuario ${actual.datos.post.userId}", style = MaterialTheme.typography.titleSmall)
                        Text("Publicación", color = TextoTenue, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item { Text(actual.datos.post.title, style = MaterialTheme.typography.headlineSmall) }
            item { Text(actual.datos.post.body, color = TextoSecundario, style = MaterialTheme.typography.bodyLarge) }
            item {
                Row(Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Comentarios", style = MaterialTheme.typography.titleLarge)
                    Surface(color = Coral, shape = CircleShape) {
                        Text("${actual.datos.comentarios.size}", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            color = Blanco, style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
            itemsIndexed(actual.datos.comentarios, key = { _, comentario -> comentario.id }) { indice, comentario ->
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AvatarUsuario(comentario.email.take(1).uppercase(), colorUsuario(indice + 1), Modifier.padding(bottom = 8.dp).size(32.dp))
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(comentario.email, modifier = Modifier.padding(start = 12.dp),
                            color = TextoTenue, style = MaterialTheme.typography.bodySmall)
                        Surface(color = Blanco, shape = RoundedCornerShape(20.dp)) {
                            Text(comentario.body, modifier = Modifier.padding(15.dp), style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
            item {
                Column(Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = { vm.eliminar(id) },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
                        enabled = eliminacion != UiState.Cargando && eliminacion !is UiState.Exito,
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (eliminacion is UiState.Exito) BordeSuave else Coral),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Coral, disabledContentColor = TextoDeshabilitado)
                    ) { Text(if (eliminacion == UiState.Cargando) "Eliminando…" else "Eliminar publicación") }
                    when (val resultado = eliminacion) {
                        null, UiState.Cargando -> Unit
                        is UiState.Exito -> MensajeExito("Publicación eliminada")
                        is UiState.Error -> ErrorConReintento(resultado.mensaje, { vm.eliminar(id) }, compacto = true)
                    }
                }
            }
        }
    }
}
