package com.ramirez.murocampus.model

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(val id: Int, val name: String, val email: String)

// [G] Paso 2: agregar aquí Post(userId, id, title, body),
// Comentario(postId, id, name, email, body) y NuevoPost(userId, title, body).
// TODO: usar @Serializable y los nombres exactos del JSON. NuevoPost no lleva id.
