package com.ramirez.campusgps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectorRadio(
    radios: List<Int>,
    seleccionado: Int,
    onSeleccionar: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item { Text("Radio") }
        items(radios, key = { it }) { radio ->
            FilterChip(
                selected = seleccionado == radio,
                onClick = { onSeleccionar(radio) },
                label = { Text("$radio m") }
            )
        }
    }
}
