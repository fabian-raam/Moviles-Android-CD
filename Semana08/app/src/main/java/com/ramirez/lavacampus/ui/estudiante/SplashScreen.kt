package com.ramirez.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramirez.lavacampus.model.Rol
import com.ramirez.lavacampus.viewmodel.SesionViewModel

@Composable
fun SplashScreen(
    vm: SesionViewModel,
    onNavegar: (String) -> Unit
) {
    val perfilActivo by vm.perfilActivo.collectAsState()

    LaunchedEffect(perfilActivo) {
        when (perfilActivo) {
            Rol.CLIENTE.name -> onNavegar(Rol.CLIENTE.name)
            Rol.ADMIN.name -> onNavegar(Rol.ADMIN.name)
            else -> onNavegar("")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}