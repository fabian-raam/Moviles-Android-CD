package com.ramirez.carrito

abstract class ItemCarrito(
    protected val nombre: String,
    protected val precio: Double
) {

    abstract fun calcularImporte(): Double

    fun obtenerNombre(): String {
        return nombre
    }

    fun obtenerPrecio(): Double {
        return precio
    }
}

class Producto(
    nombre: String,
    precio: Double,
    private var cantidad: Int
) : ItemCarrito(nombre, precio) {

    override fun calcularImporte(): Double {
        return precio * cantidad
    }

    fun obtenerCantidad(): Int {
        return cantidad
    }
}
fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS POO - TIENDA TECSUP ")
    println("=========================================")
}