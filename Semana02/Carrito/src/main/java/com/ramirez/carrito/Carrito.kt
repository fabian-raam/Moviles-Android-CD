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

class ProductoOferta(
    nombre: String,
    precio: Double,
    private var cantidad: Int
) : ItemCarrito(nombre, precio) {

    override fun calcularImporte(): Double {
        return precio * cantidad * 0.90
    }

    fun obtenerCantidad(): Int {
        return cantidad
    }
}
class Carrito {

    private val productos = mutableListOf<ItemCarrito>()

    fun agregarProducto(producto: ItemCarrito) {
        productos.add(producto)
    }

    fun calcularSubtotal(): Double {
        var subtotal = 0.0

        for (producto in productos) {
            subtotal += producto.calcularImporte()
        }

        return subtotal
    }

    fun calcularIGV(): Double {
        return calcularSubtotal() * 0.18
    }

    fun calcularTotal(): Double {
        return calcularSubtotal() + calcularIGV()
    }
    fun mostrarDetalle() {
        println("--------- DETALLE DEL CARRITO ---------")

        var i = 1

        for (producto in productos) {
            println(
                String.format(
                    "%d. %-20s S/ %8.2f",
                    i,
                    producto.obtenerNombre(),
                    producto.calcularImporte()
                )
            )

            i++
        }

        println("---------------------------------------")
    }
}

fun main() {

    println("=========================================")
    println(" CARRITO DE COMPRAS POO - TIENDA TECSUP ")
    println("=========================================")

    val carrito = Carrito()

    carrito.agregarProducto(
        Producto("Laptop HP", 2500.0, 1)
    )

    carrito.agregarProducto(
        Producto("Mouse Logitech", 45.5, 2)
    )

    carrito.agregarProducto(
        ProductoOferta("Monitor Redragon", 750.2, 3)
    )

    println("Subtotal: S/ %.2f".format(carrito.calcularSubtotal()))
    println("IGV (18%%): S/ %.2f".format(carrito.calcularIGV()))
    println("Total: S/ %.2f".format(carrito.calcularTotal()))

    println()
    carrito.mostrarDetalle()
    println()
}