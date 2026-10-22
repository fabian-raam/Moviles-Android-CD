package com.ramirez.lavacampus.data.local

import com.ramirez.lavacampus.data.local.entity.DetallePedido
import com.ramirez.lavacampus.data.local.entity.Pedido
import com.ramirez.lavacampus.data.local.entity.Servicio
import com.ramirez.lavacampus.data.local.entity.Usuario
import com.ramirez.lavacampus.model.EstadoPedido
import com.ramirez.lavacampus.model.Rol
import com.ramirez.lavacampus.model.Unidad
import java.time.LocalDate

object DatosIniciales {

    suspend fun sembrar(db: LavaDatabase) {
        // Solo sembramos si la base de datos está completamente vacía
        if (db.usuarioDao().contar() > 0) return

        // 1. SERVICIOS INICIALES
        val servicios = listOf(
            Servicio(
                nombre = "Lavado y secado",
                descripcion = "Lavado por kilogramo con secado completo",
                unidad = Unidad.KG.name,
                precio = 8.0,
                horasEntrega = 24,
                activo = true,
                icono = "wash"
            ),
            Servicio(
                nombre = "Planchado",
                descripcion = "Planchado a vapor profesional por prenda",
                unidad = Unidad.PRENDA.name,
                precio = 2.5,
                horasEntrega = 48,
                activo = true,
                icono = "iron"
            ),
            Servicio(
                nombre = "Lavado en seco",
                descripcion = "Tratamiento especial para prendas delicadas y ternos",
                unidad = Unidad.PRENDA.name,
                precio = 12.0,
                horasEntrega = 48,
                activo = true,
                icono = "shirt"
            ),
            Servicio(
                nombre = "Edredón / frazada",
                descripcion = "Lavado especial para prendas de cama pesadas",
                unidad = Unidad.UNIDAD.name,
                precio = 20.0,
                horasEntrega = 48,
                activo = false, // Inactivo inicialmente
                icono = "wash"
            )
        )
        db.servicioDao().insertarTodos(servicios)

        // 2. USUARIOS INICIALES (Códigos numéricos de 6 dígitos)
        val usuarios = listOf(
            Usuario(
                nombre = "Ana Torres",
                codigo = "202310",
                correo = "ana@correo.test",
                clave = "1234",
                rol = Rol.CLIENTE.name
            ),
            Usuario(
                nombre = "Luis Ramos",
                codigo = "202210",
                correo = "luis@correo.test",
                clave = "1234",
                rol = Rol.CLIENTE.name
            ),
            Usuario(
                nombre = "Marta Gómez",
                codigo = "202311",
                correo = "marta@correo.test",
                clave = "1234",
                rol = Rol.CLIENTE.name
            ),
            Usuario(
                nombre = "Administrador LavaCampus",
                codigo = "",
                correo = "admin@lavacampus.test",
                clave = "admin",
                rol = Rol.ADMIN.name
            )
        )
        db.usuarioDao().insertarTodos(usuarios)

        // Fechas dinámicas calculadas con java.time (relativas al día de hoy)
        val hoy = LocalDate.now().toString()
        val hace1Dia = LocalDate.now().minusDays(1).toString()
        val hace2Dias = LocalDate.now().minusDays(2).toString()
        val hace3Dias = LocalDate.now().minusDays(3).toString()

        // 3. PEDIDOS DE PRUEBA DE EJEMPLO

        // Pedido 1: Ana Torres - RECIBIDO
        val p1Id = db.pedidoDao().insertar(
            Pedido(
                usuarioId = 1,
                fechaRegistro = hoy,
                fechaEntrega = hoy,
                estado = EstadoPedido.RECIBIDO.name,
                total = 28.0,
                casillero = null,
                pagado = false,
                nota = "Cuidado con la ropa blanca"
            )
        ).toInt()

        db.detalleDao().insertarTodos(
            listOf(
                DetallePedido(pedidoId = p1Id, servicioId = 1, cantidad = 3.5, precioUnitario = 8.0, subtotal = 28.0)
            )
        )

        // Pedido 2: Ana Torres - LISTO con casillero C-05
        val p2Id = db.pedidoDao().insertar(
            Pedido(
                usuarioId = 1,
                fechaRegistro = hace1Dia,
                fechaEntrega = hoy,
                estado = EstadoPedido.LISTO.name,
                total = 40.5,
                casillero = "C-05",
                pagado = false,
                nota = ""
            )
        ).toInt()

        db.detalleDao().insertarTodos(
            listOf(
                DetallePedido(pedidoId = p2Id, servicioId = 1, cantidad = 3.5, precioUnitario = 8.0, subtotal = 28.0),
                DetallePedido(pedidoId = p2Id, servicioId = 2, cantidad = 5.0, precioUnitario = 2.5, subtotal = 12.5)
            )
        )

        // Pedido 3: Luis Ramos - LISTO con casillero C-07
        val p3Id = db.pedidoDao().insertar(
            Pedido(
                usuarioId = 2,
                fechaRegistro = hace2Dias,
                fechaEntrega = hace1Dia,
                estado = EstadoPedido.LISTO.name,
                total = 24.0,
                casillero = "C-07",
                pagado = false,
                nota = "Entregar por la tarde"
            )
        ).toInt()

        db.detalleDao().insertarTodos(
            listOf(
                DetallePedido(pedidoId = p3Id, servicioId = 3, cantidad = 2.0, precioUnitario = 12.0, subtotal = 24.0)
            )
        )

        // Pedido 4: Marta Gómez - ENTREGADO y Pagado
        val p4Id = db.pedidoDao().insertar(
            Pedido(
                usuarioId = 3,
                fechaRegistro = hace3Dias,
                fechaEntrega = hace1Dia,
                estado = EstadoPedido.ENTREGADO.name,
                total = 32.0,
                casillero = "C-12",
                pagado = true,
                nota = ""
            )
        ).toInt()

        db.detalleDao().insertarTodos(
            listOf(
                DetallePedido(pedidoId = p4Id, servicioId = 1, cantidad = 4.0, precioUnitario = 8.0, subtotal = 32.0)
            )
        )
    }
}
