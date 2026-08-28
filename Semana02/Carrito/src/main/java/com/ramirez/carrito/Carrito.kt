package com.ramirez.carrito

abstract class ItemCarrito(
    val nombre: String,
    val precio: Double
) {
    abstract fun calcularImporte(): Double
}

class Producto(
    nombre: String,
    precio: Double,
    val cantidad: Int
) : ItemCarrito(nombre, precio) {

    override fun calcularImporte(): Double {
        return precio * cantidad
    }
}

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS POO - TIENDA TECSUP ")
    println("=========================================")
}