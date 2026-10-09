package com.ramirez.saludplus.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ramirez.saludplus.R
import com.ramirez.saludplus.navigation.Rutas

@Composable
fun SplashScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            //Ocupara toda la pantalla
            .fillMaxSize()
            //El padding da separacion del contenedor donde esta
            .padding(horizontal = 20.dp, vertical = 30.dp),
        //Para dar posicion de manera vertical
        verticalArrangement = Arrangement.SpaceBetween,
        //Para dar posicion de manera horizontal --
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Parte 1: Logo + Titulo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de SaludPlus",
                //Heigh es para darle una altura a la imagen
                modifier = Modifier.height(70.dp),
                //sirve para decidir q hacer con la imagen con
                //el espacio q le dan
                //fit hace q se achique, crop corta la imagen
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Clínica",
                fontSize = 40.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "SaludPlus",
                fontSize = 45.sp,
                //Controla el grosor de la typografia
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Tu salud, nuestra prioridad",
                fontSize = 20.sp,
                //onSurfaceVariant variante de color
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Parte 2: Contenedor Box que limita la altura de la imagen
        Box(
            modifier = Modifier
                //Esto le dice al box que use todo el espacio libre horizontal --
                .fillMaxWidth()
                //Esto le dice al box que use todo el espacio libre vertical //
                .weight(1f)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.foto_doctor_splash),
                contentDescription = "Foto doctor",
                modifier = Modifier
                    //le dice a la imagen que use 85% del espacio del box
                    .fillMaxHeight(0.85f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }

        //Parte 3: Boton + Texto
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Button(
                onClick = { navController.navigate(Rutas.REGISTRO) },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Comenzar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { navController.navigate(Rutas.LOGIN) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Ya tengo una cuenta",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}