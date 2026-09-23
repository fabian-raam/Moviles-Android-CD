package com.ramirez.citas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    onHomeClick: () -> Unit,
    onAppointmentsClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(text = "JP")
        Text(text = "Juan Pérez")
        Text(text = "Paciente")

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        HorizontalDivider()

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        NavigationDrawerItem(
            label = {
                Text("Inicio")
            },
            selected = false,
            onClick = onHomeClick
        )

        NavigationDrawerItem(
            label = {
                Text("Mis citas")
            },
            selected = false,
            onClick = onAppointmentsClick
        )

        NavigationDrawerItem(
            label = {
                Text("Historial médico")
            },
            selected = false,
            onClick = onHistoryClick
        )

        NavigationDrawerItem(
            label = {
                Text("Perfil")
            },
            selected = false,
            onClick = onProfileClick
        )
    }
}