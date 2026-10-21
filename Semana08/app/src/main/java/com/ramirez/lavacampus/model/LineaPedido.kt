package com.ramirez.lavacampus.model

import com.ramirez.lavacampus.data.local.entity.Servicio

data class LineaPedido(
    val servicio: Servicio,
    val cantidad: Double
) {
    val subtotal: Double
        get() = servicio.precio * cantidad
}