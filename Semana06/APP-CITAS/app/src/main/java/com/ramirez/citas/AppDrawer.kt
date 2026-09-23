package com.ramirez.citas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    onHomeClick: () -> Unit,
    onAppointmentsClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    Column(
        modifier = Modifier.padding(20.dp)
    ) {

        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {

            Text(
                text = "JP",
                modifier = Modifier.padding(18.dp),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Juan Pérez",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Paciente",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        HorizontalDivider()

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        NavigationDrawerItem(
            label = {
                Text("⌂  Inicio")
            },
            selected = true,
            onClick = onHomeClick
        )

        NavigationDrawerItem(
            label = {
                Text("▣  Mis citas")
            },
            selected = false,
            onClick = onAppointmentsClick
        )

        NavigationDrawerItem(
            label = {
                Text("✚  Historial médico")
            },
            selected = false,
            onClick = onHistoryClick
        )

        NavigationDrawerItem(
            label = {
                Text("●  Perfil")
            },
            selected = false,
            onClick = onProfileClick
        )
    }
}