package com.ramirez.murocampus.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.murocampus.ui.theme.*

fun colorUsuario(id: Int): Color = when ((id - 1).mod(3)) {
    0 -> NaranjaAvatar
    1 -> TurquesaAvatar
    else -> VerdeAvatar
}

@Composable
fun AvatarUsuario(etiqueta: String, color: Color, modifier: Modifier = Modifier) {
    Box(modifier.size(38.dp).background(color, CircleShape), contentAlignment = Alignment.Center) {
        Text(etiqueta, color = Blanco, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun MarcaMuroCampus(modifier: Modifier = Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(SpanStyle(color = TextoPrincipal)) { append("Muro") }
            withStyle(SpanStyle(color = CoralMarca)) { append("Campus") }
        },
        modifier = modifier,
        style = MaterialTheme.typography.displaySmall,
        letterSpacing = (-1.2).sp
    )
}

@Composable
fun MenuInferior(onMuro: () -> Unit, onNueva: () -> Unit) {
    Surface(color = Color(0xFFF8F8FA)) {
        Column {
            HorizontalDivider(color = Color(0xFFD1D1D8), thickness = 1.dp)
            Row(Modifier.fillMaxWidth().navigationBarsPadding()) {
                Column(
                    Modifier.weight(1f).clickable(onClick = onMuro).padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconoMuro(color = Coral)
                    Text("Muro", style = MaterialTheme.typography.labelMedium, color = Coral)
                }
                Column(
                    Modifier.weight(1f).clickable(onClick = onNueva).padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconoNueva(color = TextoTenue)
                    Text("Nueva", style = MaterialTheme.typography.labelMedium, color = TextoTenue)
                }
            }
        }
    }
}

@Composable
fun TarjetaPost(
    userId: Int,
    titulo: String,
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Blanco, contentColor = TextoPrincipal)
    ) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarUsuario("U$userId", colorUsuario(userId))
                Spacer(Modifier.width(10.dp))
                Text("Usuario $userId", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleSmall)
                IconoSiguiente(Modifier.size(18.dp), color = Color(0xFFC5C5CE))
            }
            Text(titulo, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(texto, style = MaterialTheme.typography.bodyMedium, color = TextoSecundario, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun Cargando(modifier: Modifier = Modifier, mensaje: String = "Cargando publicaciones…") {
    Column(
        modifier.fillMaxWidth().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CircularProgressIndicator(color = Coral, trackColor = CoralSuave, strokeWidth = 3.dp)
        Text(mensaje, style = MaterialTheme.typography.bodyMedium, color = TextoSecundario, textAlign = TextAlign.Center)
    }
}

@Composable
fun BotonCoral(texto: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.heightIn(min = 52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Coral, contentColor = Blanco,
            disabledContainerColor = Deshabilitado, disabledContentColor = TextoDeshabilitado
        )
    ) { Text(texto, style = MaterialTheme.typography.labelLarge) }
}

@Composable
fun ErrorConReintento(
    mensaje: String,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier,
    compacto: Boolean = false
) {
    val sinConexion = mensaje.startsWith("Sin conexión")
    if (compacto) {
        Surface(modifier.fillMaxWidth(), color = CoralSuave, shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(mensaje, style = MaterialTheme.typography.bodyMedium, color = TextoPrincipal)
                BotonCoral("Reintentar", onReintentar)
            }
        }
    } else {
        Column(
            modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(Modifier.size(90.dp).background(CoralSuave, CircleShape), contentAlignment = Alignment.Center) {
                IconoSinConexion(Modifier.size(42.dp))
            }
            Spacer(Modifier.height(24.dp))
            Text(
                if (sinConexion) "Sin conexión a internet" else mensaje.substringBefore('\n'),
                style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                if (sinConexion) "Revisa tu red e inténtalo otra vez." else "Inténtalo otra vez en un momento.",
                color = TextoSecundario, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(26.dp))
            Button(
                onClick = onReintentar,
                modifier = Modifier.widthIn(min = 156.dp).heightIn(min = 50.dp)
                    .shadow(9.dp, CircleShape, ambientColor = Coral.copy(alpha = 0.25f), spotColor = Coral.copy(alpha = 0.25f)),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Coral, contentColor = Blanco)
            ) { Text("Reintentar", style = MaterialTheme.typography.labelLarge) }
        }
    }
}

@Composable
fun MensajeExito(texto: String, modifier: Modifier = Modifier) {
    Surface(modifier.fillMaxWidth(), color = FondoExito, shape = RoundedCornerShape(14.dp)) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            IconoExito(color = VerdeExito)
            Text(texto, color = VerdeExito, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
