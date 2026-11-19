package com.ramirez.campusgps.ui

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.campusgps.R
import com.ramirez.campusgps.ui.theme.CampusgpsTheme
import com.ramirez.campusgps.ui.theme.Blanco
import com.ramirez.campusgps.ui.theme.Negro

@Composable
fun FiltroCategorias(
    categorias: List<String>,
    seleccionada: String?,
    onSeleccionar: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.selectableGroup(),
        // El padre aporta el margen horizontal; este espacio protege las sombras verticales.
        contentPadding = PaddingValues(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ChipCategoria(
                texto = "Todas",
                icono = R.drawable.ic_categorias,
                seleccionada = seleccionada == null,
                onClick = { onSeleccionar(null) }
            )
        }
        items(categorias, key = { it }) { categoria ->
            ChipCategoria(
                texto = categoria,
                icono = iconoDeCategoria(categoria),
                seleccionada = seleccionada == categoria,
                onClick = { onSeleccionar(categoria) }
            )
        }
    }
}

private fun iconoDeCategoria(categoria: String): Int = when (categoria) {
    "Estudio" -> R.drawable.ic_estudio
    "Comida" -> R.drawable.ic_comida
    "Eventos" -> R.drawable.ic_eventos
    "Servicios" -> R.drawable.ic_servicios
    else -> R.drawable.ic_categorias
}

// ESTILO DE CATEGORIAS: pildoras de 44 dp con seleccion y colores animados.
@Composable
private fun ChipCategoria(
    texto: String,
    icono: Int,
    seleccionada: Boolean,
    onClick: () -> Unit
) {
    val fondo by animateColorAsState(
        targetValue = if (seleccionada) {
            Negro
        } else {
            MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(180),
        label = "Fondo de $texto"
    )
    val contenido by animateColorAsState(
        targetValue = if (seleccionada) Blanco else MaterialTheme.colorScheme.onSurface,
        animationSpec = tween(180),
        label = "Texto e icono de $texto"
    )
    val forma = RoundedCornerShape(22.dp)

    Surface(
        modifier = Modifier
            .height(44.dp)
            .shadow(if (seleccionada) 4.dp else 1.dp, forma)
            .clip(forma)
            .selectable(
                selected = seleccionada,
                role = Role.Tab,
                onClick = onClick
            )
            .semantics {
                stateDescription = if (seleccionada) "Seleccionado" else "No seleccionado"
            },
        shape = forma,
        color = fondo,
        contentColor = contenido
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(icono),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = texto,
                style = MaterialTheme.typography.labelLarge,
                fontSize = 14.sp,
                fontWeight = if (seleccionada) FontWeight.SemiBold else FontWeight.Medium,
                maxLines = 1
            )
        }
    }
}

@Preview(name = "Todas", showBackground = true, backgroundColor = 0xFFE6EAF0, widthDp = 380)
@Composable
private fun FiltroTodasPreview() {
    CampusgpsTheme(darkTheme = false) {
        FiltroCategorias(listOf("Estudio", "Comida", "Eventos", "Servicios"), null, {})
    }
}

@Preview(name = "Comida", showBackground = true, backgroundColor = 0xFFE6EAF0, widthDp = 380)
@Composable
private fun FiltroComidaPreview() {
    CampusgpsTheme(darkTheme = false) {
        FiltroCategorias(listOf("Estudio", "Comida", "Eventos", "Servicios"), "Comida", {})
    }
}

@Preview(
    name = "Categorias oscuras",
    showBackground = true,
    backgroundColor = 0xFFE6EAF0,
    widthDp = 380,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun FiltroOscuroPreview() {
    CampusgpsTheme(darkTheme = true) {
        FiltroCategorias(listOf("Estudio", "Comida", "Eventos", "Servicios"), null, {})
    }
}
