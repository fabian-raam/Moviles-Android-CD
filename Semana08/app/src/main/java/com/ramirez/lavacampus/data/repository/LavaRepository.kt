package com.ramirez.lavacampus.data.repository

import com.ramirez.lavacampus.data.local.entity.Pedido
import com.ramirez.lavacampus.data.local.entity.Servicio
import com.ramirez.lavacampus.data.local.entity.Usuario
import com.ramirez.lavacampus.data.local.relation.PedidoConDetalle
import com.ramirez.lavacampus.model.ClienteResumen
import com.ramirez.lavacampus.model.EstadoPedido
import com.ramirez.lavacampus.model.IngresoServicio
import com.ramirez.lavacampus.model.LineaPedido
import kotlinx.coroutines.flow.Flow

interface LavaRepository {
    // Usuarios
    suspend fun login(correo: String, clave: String): Usuario?
    suspend fun registrarEstudiante(nombre: String, codigo: String, correo: String, clave: String): Long
    fun observarUsuario(id: Int): Flow<Usuario?>
    fun observarClientes(): Flow<List<ClienteResumen>>

    // Servicios
    fun observarServicios(): Flow<List<Servicio>>
    fun observarServiciosActivos(): Flow<List<Servicio>>
    suspend fun guardarServicio(s: Servicio)
    suspend fun eliminarServicio(s: Servicio)
    suspend fun cambiarActivo(id: Int, activo: Boolean)
    suspend fun servicioTieneDetalles(id: Int): Boolean

    // Pedidos
    suspend fun crearPedido(
        usuarioId: Int,
        lineas: List<LineaPedido>,
        fechaEntrega: String,
        nota: String,
        descuento: Double = 0.0
    ): Long

    suspend fun contarActivos(usuarioId: Int): Int
    fun observarPedidosDeUsuario(usuarioId: Int): Flow<List<PedidoConDetalle>>
    fun observarPedidos(): Flow<List<PedidoConDetalle>>
    fun observarPedido(id: Int): Flow<PedidoConDetalle?>
    suspend fun cambiarEstado(id: Int, estado: EstadoPedido, casillero: String?)
    suspend fun marcarPagado(id: Int, pagado: Boolean)
    suspend fun calificarPedido(id: Int, calificacion: Int)
    fun observarCasillerosOcupados(): Flow<List<String>>

    // Panel
    fun observarPedidosDeFecha(fecha: String): Flow<Int>
    fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int>
    fun observarIngresosDesde(fechaInicio: String): Flow<Double>
    fun observarIngresosPorServicio(fechaInicio: String): Flow<List<IngresoServicio>>
    fun observarPromedioCalificacion(): Flow<Double>
    fun observarGastoMensual(usuarioId: Int): Flow<List<com.ramirez.lavacampus.data.local.dao.GastoMensual>>
}
