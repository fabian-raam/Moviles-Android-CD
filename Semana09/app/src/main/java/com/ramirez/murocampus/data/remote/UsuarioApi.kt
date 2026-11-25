package com.ramirez.murocampus.data.remote

import com.ramirez.murocampus.model.Usuario
import retrofit2.http.GET

interface UsuarioApi {
    @GET("users")
    suspend fun listar(): List<Usuario>
}
