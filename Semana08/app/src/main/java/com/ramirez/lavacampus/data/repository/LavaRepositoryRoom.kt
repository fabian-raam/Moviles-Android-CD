package com.ramirez.lavacampus.data.repository

import androidx.room.withTransaction
import com.ramirez.lavacampus.data.local.LavaDatabase
import com.ramirez.lavacampus.data.local.entity.DetallePedido
import com.ramirez.lavacampus.data.local.entity.Pedido
import com.ramirez.lavacampus.data.local.entity.Servicio
import com.ramirez.lavacampus.data.local.entity.Usuario
import com.ramirez.lavacampus.data.local.relation.PedidoConDetalle
import com.ramirez.lavacampus.model.ClienteResumen
import com.ramirez.lavacampus.model.EstadoPedido
import com.ramirez.lavacampus.model.IngresoServicio
import com.ramirez.lavacampus.model.LineaPedido
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class LavaRepositoryRoom(private val db: LavaDatabase) : LavaRepository {

    // === USUARIOS ===
    override suspend fun login(correo: String, clave: String): Usuario? {
        return db.usuarioDao().login(correo, clave)
    }

    override fun observarClientes(): Flow<List<ClienteResumen>> {
        return db.usuarioDao().observarClientes()
    }

    // === SERVICIOS ===
    override fun observarServicios(): Flow<List<Servicio>> {
        return db.servicioDao().observarTodos()
    }

    override fun observarServiciosActivos(): Flow<List<Servicio>> {
        return db.servicioDao().observarActivos()
    }

    override suspend fun guardarServicio(s: Servicio) {
        db.servicioDao().insertar(s)
    }

    override suspend fun eliminarServicio(s: Servicio) {
        db.servicioDao().eliminar(s)
    }

    override suspend fun cambiarActivo(id: Int, activo: Boolean) {
        db.servicioDao().cambiarActivo(id, activo)
    }

    override suspend fun servicioTieneDetalles(id: Int): Boolean {
        return db.detalleDao().contarPorServicio(id) > 0
    }

    // === PEDIDOS ===
    override suspend fun crearPedido(
        usuarioId: Int,
        lineas: List<LineaPedido>,
        fechaEntrega: String,
        nota: String
    ): Long {
        return db.withTransaction {
            val lineasValidas = lineas.filter { it.cantidad > 0 }
            val totalPedido = lineasValidas.sumOf { it.subtotal }
            val fechaHoy = LocalDate.now().toString()

            val nuevoPedido = Pedido(
                usuarioId = usuarioId,
                fechaRegistro = fechaHoy,
                fechaEntrega = fechaEntrega,
                estado = EstadoPedido.RECIBIDO.name,
                total = totalPedido,
                casillero = null,
                pagado = false,
                nota = nota
            )

            val pedidoId = db.pedidoDao().insertar(nuevoPedido)

            val detalles = lineasValidas.map { linea ->
                DetallePedido(
                    pedidoId = pedidoId.toInt(),
                    servicioId = linea.servicio.id,
                    cantidad = linea.cantidad,
                    precioUnitario = linea.servicio.precio,
                    subtotal = linea.subtotal
                )
            }

            db.detalleDao().insertarTodos(detalles)
            pedidoId
        }
    }

    override suspend fun contarActivos(usuarioId: Int): Int {
        return db.pedidoDao().contarActivos(usuarioId)
    }

    override fun observarPedidosDeUsuario(usuarioId: Int): Flow<List<PedidoConDetalle>> {
        return db.pedidoDao().observarPorUsuario(usuarioId)
    }

    override fun observarPedidos(): Flow<List<PedidoConDetalle>> {
        return db.pedidoDao().observarTodos()
    }

    override fun observarPedido(id: Int): Flow<PedidoConDetalle?> {
        return db.pedidoDao().observarUno(id)
    }

    override suspend fun cambiarEstado(id: Int, estado: EstadoPedido, casillero: String?) {
        db.pedidoDao().actualizarEstado(id, estado.name, casillero)
    }

    override suspend fun marcarPagado(id: Int, pagado: Boolean) {
        db.pedidoDao().actualizarPagado(id, pagado)
    }

    override fun observarCasillerosOcupados(): Flow<List<String>> {
        return db.pedidoDao().observarCasillerosOcupados()
    }

    // === PANEL DE CONTROL ===
    override fun observarPedidosDeFecha(fecha: String): Flow<Int> {
        return db.pedidoDao().contarPorFecha(fecha)
    }

    override fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int> {
        return db.pedidoDao().contarPorEstado(estado.name)
    }

    override fun observarIngresosDesde(fechaInicio: String): Flow<Double> {
        return db.pedidoDao().ingresosDesde(fechaInicio)
    }

    override fun observarIngresosPorServicio(fechaInicio: String): Flow<List<IngresoServicio>> {
        return db.detalleDao().observarIngresosPorServicio(fechaInicio)
    }
}