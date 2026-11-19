package com.ramirez.campusgps.ui

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.campusgps.ui.theme.CampusgpsTheme

// Color especifico de la referencia, centralizado para ajustar el selector.
private val FondoSelectorClaro = Color(0xFFE6EAF0)

@Composable
fun SelectorRadio(
    radios: List<Int>,
    seleccionado: Int,
    onSeleccionar: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val colores = MaterialTheme.colorScheme
    val fondo = if (colores.surface.luminance() > 0.5f) {
        FondoSelectorClaro
    } else {
        colores.surfaceVariant
    }

    // SELECTOR DE RADIO: tres segmentos iguales dentro de un control de 184 x 40 dp.
    // El contenedor externo permite conservar el ancho del control con el modifier del caller.
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier
                .width(184.dp)
                .height(40.dp)
                .background(fondo, RoundedCornerShape(10.dp))
                .padding(horizontal = 2.dp)
                .selectableGroup()
        ) {
            radios.forEach { radio ->
                val activo = seleccionado == radio
                val colorSegmento by animateColorAsState(
                    targetValue = if (activo) colores.surface else Color.Transparent,
                    animationSpec = tween(durationMillis = 180),
                    label = "Fondo del radio $radio"
                )

                // Toda la celda mide 40 dp y responde al toque; la superficie visible mide 36 dp.
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .selectable(
                            selected = activo,
                            role = Role.RadioButton,
                            onClick = { onSeleccionar(radio) }
                        )
                        .semantics {
                            contentDescription = "$radio metros"
                            stateDescription = if (activo) "Seleccionado" else "No seleccionado"
                        }
                        .padding(vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth().height(36.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = colorSegmento,
                        shadowElevation = if (activo) 1.dp else 0.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "$radio m",
                                modifier = Modifier.clearAndSetSemantics { },
                                style = MaterialTheme.typography.labelLarge,
                                fontSize = 14.sp,
                                fontWeight = if (activo) FontWeight.SemiBold else FontWeight.Medium,
                                color = colores.onSurface,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Radio 100 m", showBackground = true)
@Composable
private fun SelectorRadio100Preview() {
    CampusgpsTheme(darkTheme = false) {
        SelectorRadio(listOf(100, 200, 500), 100, {})
    }
}

@Preview(name = "Radio 200 m", showBackground = true)
@Composable
private fun SelectorRadio200Preview() {
    CampusgpsTheme(darkTheme = false) {
        SelectorRadio(listOf(100, 200, 500), 200, {})
    }
}

@Preview(name = "Radio 500 m", showBackground = true)
@Composable
private fun SelectorRadio500Preview() {
    CampusgpsTheme(darkTheme = false) {
        SelectorRadio(listOf(100, 200, 500), 500, {})
    }
}

@Preview(name = "Radio oscuro", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun SelectorRadioOscuroPreview() {
    CampusgpsTheme(darkTheme = true) {
        SelectorRadio(listOf(100, 200, 500), 100, {})
    }
}
