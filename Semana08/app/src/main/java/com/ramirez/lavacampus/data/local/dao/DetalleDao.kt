package com.ramirez.lavacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ramirez.lavacampus.data.local.entity.DetallePedido
import com.ramirez.lavacampus.model.IngresoServicio
import kotlinx.coroutines.flow.Flow

@Dao
interface DetalleDao {

    // 1. Guarda todas las líneas de detalle de un pedido al mismo tiempo
    @Insert
    suspend fun insertarTodos(lista: List<DetallePedido>)

    // 2. Para la regla de negocio: "Un servicio con pedidos registrados no se puede eliminar"
    @Query("SELECT COUNT(*) FROM detalles_pedido WHERE servicioId = :servicioId")
    suspend fun contarPorServicio(servicioId: Int): Int

    // 3. Consulta para el gráfico de barras: calcula los ingresos por servicio de pedidos pagados esta semana
    @Query("""
        SELECT s.nombre AS nombre, COALESCE(SUM(d.subtotal), 0.0) AS total
        FROM detalles_pedido d
        INNER JOIN servicios s ON d.servicioId = s.id
        INNER JOIN pedidos p ON d.pedidoId = p.id
        WHERE p.pagado = 1 AND p.fechaRegistro >= :fechaInicio
        GROUP BY s.id
        ORDER BY total DESC
    """)
    fun observarIngresosPorServicio(fechaInicio: String): Flow<List<IngresoServicio>>
}