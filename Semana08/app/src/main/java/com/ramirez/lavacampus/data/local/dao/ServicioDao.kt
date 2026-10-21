package com.ramirez.lavacampus.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ramirez.lavacampus.data.local.entity.Servicio
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {
    //READ: todo los servicios
    @Query("SELECT * FROM servicios ORDER BY nombre")
    fun observarTodos(): Flow<List<Servicio>>

    @Query("SELECT * FROM servicios WHERE activo = 1 ORDER BY nombre")
    fun observarActivos(): Flow<List<Servicio>>

    @Query("SELECT * FROM servicios WHERE id = :id")
    suspend fun obtener(id : Int): Servicio?

    @Query("SELECT COUNT(*) FROM servicios")
    suspend fun contar(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //le dice que si existe uno con esos datos los remplace
    suspend fun insertar(s: Servicio): Long
    //recibe un objeto de tipo servicio

    @Insert
    suspend fun insertarTodos(lista: List<Servicio>)
    //permite insertar una lista junta

    @Update
    suspend fun actualizar(s: Servicio)
    //permite actualizar una fila en general

    @Delete
    suspend fun eliminar(s: Servicio)
    //permite eliminar una fila en general

    @Query("UPDATE servicios SET activo = :activo WHERE id = :id")
    suspend fun cambiarActivo(id: Int, activo: Boolean)
    //especifica que campo queremos cambiar
}