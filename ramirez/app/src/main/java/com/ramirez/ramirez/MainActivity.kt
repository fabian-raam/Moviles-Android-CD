package com.ramirez.ramirez

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.FirebaseApp
import com.ramirez.ramirez.ui.AuthApp
import com.ramirez.ramirez.ui.theme.RamirezTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar Firebase (con protección contra falta de google-services.json)
        try {
            FirebaseApp.initializeApp(this)
        } catch (_: Exception) {
        }

        setContent {
            RamirezTheme {
                AuthApp()
            }
        }
    }
}
