package com.ramirez.lavacampus.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Iron
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun IconoServicio(
    icono: String,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    val vector = when (icono.lowercase()) {
        "iron" -> Icons.Default.Iron
        "shirt" -> Icons.Default.Checkroom
        else -> Icons.Default.LocalLaundryService
    }

    Icon(
        imageVector = vector,
        contentDescription = icono,
        modifier = modifier,
        tint = tint
    )
}