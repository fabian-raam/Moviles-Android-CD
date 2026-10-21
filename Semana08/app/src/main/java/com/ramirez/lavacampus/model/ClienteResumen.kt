package com.ramirez.lavacampus.model

data class ClienteResumen(
    val usuarioId: Int,
    val nombre: String,
    val codigo: String,
    val correo: String,
    val cantidadPedidos: Int,
    val totalGastado: Double
)