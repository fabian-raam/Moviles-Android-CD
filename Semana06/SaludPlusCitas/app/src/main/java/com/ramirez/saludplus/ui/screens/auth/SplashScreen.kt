package com.ramirez.saludplus.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ramirez.saludplus.navigation.Rutas

@Composable
fun SplashScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            //Ocupara toda la pantalla
            .fillMaxSize()
            //El padding da separacion del contenedor donde esta
            .padding(24.dp),
            //Para dar posicion de manera vertical /
            verticalArrangement =  Arrangement.Center,
            //Para dar posicion de manera horizontal --
            horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Clinica SaludPlus")
        Text("Tu salud, nuestra prioridad")
        //Dar espacio entre los elementos q lo rodean
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {navController.navigate(Rutas.LOGIN)}
        ) {
            Text("Comenzar")
        }
        TextButton(
            onClick = {navController.navigate(Rutas.LOGIN)}
        ) {
            Text("Ya tengo una cuenta")
        }
    }
}
