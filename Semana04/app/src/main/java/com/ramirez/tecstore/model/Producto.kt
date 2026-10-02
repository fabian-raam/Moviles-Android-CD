package com.ramirez.tecstore.model

/**
 * Data class que representa un producto del carrito de compras TECSUP.
 *
 * @property nombre Nombre del producto
 * @property precio Precio unitario del producto en Soles (S/)
 * @property cantidad Cantidad del producto
 * @property esFavorito Indica si el producto ha sido marcado como favorito por el usuario
 */
data class Producto(
    val nombre: String,
    val precio: Double,
    val cantidad: Int,
    val esFavorito: Boolean = false
)
