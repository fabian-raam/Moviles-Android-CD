package com.ramirez.murocampus.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

// Recibe valores simples para poder utilizarse antes de implementar Post.
@Composable
fun TarjetaPost(
    userId: Int,
    titulo: String,
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Usuario $userId", color = MaterialTheme.colorScheme.primary)
            Text(
                titulo,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(texto, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun Cargando(
    modifier: Modifier = Modifier,
    mensaje: String = "Cargando publicaciones…"
) {
    Column(
        modifier.fillMaxWidth().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CircularProgressIndicator()
        Text(mensaje)
    }
}

@Composable
fun ErrorConReintento(
    mensaje: String,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(mensaje)
            Button(onClick = onReintentar) { Text("Reintentar") }
        }
    }
}
