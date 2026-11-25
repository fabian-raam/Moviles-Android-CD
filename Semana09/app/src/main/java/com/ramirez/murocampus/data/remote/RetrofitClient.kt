package com.ramirez.murocampus.data.remote

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

// [G] Paso 4. UsuarioRemote.kt contiene el ejemplo resuelto.
object RetrofitClient {
    private val json = Json { ignoreUnknownKeys = true }
    private val cliente = OkHttpClient.Builder()
        // TODO: agregar HttpLoggingInterceptor con nivel BODY solo en desarrollo.
        .build()

    // TODO: después de crear PostApi en el paso 3, declarar:
    // val api: PostApi by lazy { ... }
    // Usar baseUrl HTTPS terminada en /, cliente y conversor kotlinx.serialization.
    // No usar este esqueleto hasta completar el paso 4.
}
