package com.ramirez.lavacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ramirez.lavacampus.data.local.entity.Pedido
import com.ramirez.lavacampus.data.local.relation.PedidoConDetalle
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {

    // === 1. ESCRITURA Y ACTUALIZACIONES ===

    @Insert
    suspend fun insertar(p: Pedido): Long

    @Query("UPDATE pedidos SET estado = :estado, casillero = :casillero WHERE id = :id")
    suspend fun actualizarEstado(id: Int, estado: String, casillero: String?)

    @Query("UPDATE pedidos SET pagado = :pagado WHERE id = :id")
    suspend fun actualizarPagado(id: Int, pagado: Boolean)

    @Query("SELECT * FROM pedidos WHERE id = :id")
    suspend fun obtener(id: Int): Pedido?


    // === 2. LECTURAS EN TIEMPO REAL (Usa @Transaction para armar PedidoConDetalle) ===

    @Transaction
    @Query("SELECT * FROM pedidos WHERE usuarioId = :usuarioId ORDER BY id DESC")
    fun observarPorUsuario(usuarioId: Int): Flow<List<PedidoConDetalle>>

    @Transaction
    @Query("SELECT * FROM pedidos ORDER BY id DESC")
    fun observarTodos(): Flow<List<PedidoConDetalle>>

    @Transaction
    @Query("SELECT * FROM pedidos WHERE id = :id")
    fun observarUno(id: Int): Flow<PedidoConDetalle?>


    // === 3. REGLAS DE NEGOCIO ===

    // Para la regla de máximo 3 pedidos activos por estudiante
    @Query("SELECT COUNT(*) FROM pedidos WHERE usuarioId = :usuarioId AND estado IN ('RECIBIDO', 'EN_PROCESO', 'LISTO')")
    suspend fun contarActivos(usuarioId: Int): Int

    // Para evitar asignar un casillero ya ocupado
    @Query("SELECT casillero FROM pedidos WHERE estado = 'LISTO' AND casillero IS NOT NULL")
    fun observarCasillerosOcupados(): Flow<List<String>>


    // === 4. PANEL DE CONTROL (ADMINISTRADOR) ===

    // Contar pedidos de hoy
    @Query("SELECT COUNT(*) FROM pedidos WHERE fechaRegistro = :fecha AND estado != 'CANCELADO'")
    fun contarPorFecha(fecha: String): Flow<Int>

    // Contar pedidos por estado para el gráfico de dona
    @Query("SELECT COUNT(*) FROM pedidos WHERE estado = :estado")
    fun contarPorEstado(estado: String): Flow<Int>

    // Suma de ingresos cobrados desde el lunes
    @Query("SELECT COALESCE(SUM(total), 0.0) FROM pedidos WHERE pagado = 1 AND fechaRegistro >= :fechaInicio")
    fun ingresosDesde(fechaInicio: String): Flow<Double>
}