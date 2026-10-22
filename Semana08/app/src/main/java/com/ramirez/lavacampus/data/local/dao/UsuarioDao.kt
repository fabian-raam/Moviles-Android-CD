package com.ramirez.lavacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ramirez.lavacampus.data.local.entity.Usuario
import com.ramirez.lavacampus.model.ClienteResumen
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND clave = :clave")
    suspend fun login(correo: String, clave: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE id = :id")
    fun observarPorId(id: Int): Flow<Usuario?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registrar(u: Usuario): Long

    @Insert
    suspend fun insertarTodos(lista: List<Usuario>)

    @Query("SELECT COUNT(*) FROM usuarios")
    suspend fun contar(): Int

    @Query("""
        SELECT u.id AS usuarioId, u.nombre AS nombre, u.codigo AS codigo, u.correo AS correo,
               COUNT(p.id) AS cantidadPedidos,
               COALESCE(SUM(CASE WHEN p.pagado = 1 THEN p.total ELSE 0 END), 0.0) AS totalGastado
        FROM usuarios u
        LEFT JOIN pedidos p ON u.id = p.usuarioId AND p.estado != 'CANCELADO'
        WHERE u.rol = 'CLIENTE'
        GROUP BY u.id
        ORDER BY u.nombre
    """)
    fun observarClientes(): Flow<List<ClienteResumen>>
}
