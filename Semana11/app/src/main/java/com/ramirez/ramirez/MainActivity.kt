package com.ramirez.ramirez

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 FIREBASE: Inicializa los servicios del SDK de Firebase en la aplicación
        FirebaseApp.initializeApp(this)

        setContent {
            AuthApp()
        }
    }
}
