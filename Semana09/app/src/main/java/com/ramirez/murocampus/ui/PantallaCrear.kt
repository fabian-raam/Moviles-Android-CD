package com.ramirez.murocampus.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramirez.murocampus.ui.theme.*
import com.ramirez.murocampus.viewmodel.PostsViewModel
import com.ramirez.murocampus.viewmodel.UiState

@Composable
private fun CampoPublicacion(
    etiqueta: String, valor: String, placeholder: String,
    onValueChange: (String) -> Unit, habilitado: Boolean, multilinea: Boolean
) {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(etiqueta.uppercase(), color = Coral, style = MaterialTheme.typography.labelSmall)
        BasicTextField(
            value = valor, onValueChange = onValueChange, enabled = habilitado,
            singleLine = !multilinea, minLines = if (multilinea) 6 else 1,
            modifier = Modifier.fillMaxWidth().semantics { contentDescription = etiqueta },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = TextoPrincipal),
            cursorBrush = SolidColor(Coral),
            decorationBox = { campo ->
                Box {
                    if (valor.isEmpty()) Text(placeholder, color = Color(0xFFB4B4BF), style = MaterialTheme.typography.bodyLarge)
                    campo()
                }
            }
        )
    }
}

@Composable
fun PantallaCrear(vm: PostsViewModel, modifier: Modifier = Modifier) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var texto by rememberSaveable { mutableStateOf("") }
    val estado by vm.publicacion.collectAsStateWithLifecycle()
    val publicando = estado == UiState.Cargando

    LaunchedEffect(estado) {
        if (estado is UiState.Exito) { titulo = ""; texto = "" }
    }

    Column(
        modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Surface(color = Blanco, shape = RoundedCornerShape(24.dp)) {
            Column {
                CampoPublicacion("Título", titulo, "Escribe un título", {
                    titulo = it; vm.limpiarResultadoPublicacion()
                }, !publicando, false)
                HorizontalDivider(Modifier.padding(horizontal = 16.dp), color = BordeSuave)
                CampoPublicacion("Contenido", texto, "¿Qué quieres compartir?", {
                    texto = it; vm.limpiarResultadoPublicacion()
                }, !publicando, true)
            }
        }
        BotonCoral("Publicar", { vm.publicar(titulo, texto) }, Modifier.fillMaxWidth(),
            enabled = titulo.isNotBlank() && texto.isNotBlank() && !publicando)
        when (val actual = estado) {
            null -> Unit
            UiState.Cargando -> Cargando(mensaje = "Publicando…")
            is UiState.Exito -> MensajeExito("Publicado con id ${actual.datos.id}")
            is UiState.Error -> ErrorConReintento(actual.mensaje, { vm.publicar(titulo, texto) }, compacto = true)
        }
    }
}
