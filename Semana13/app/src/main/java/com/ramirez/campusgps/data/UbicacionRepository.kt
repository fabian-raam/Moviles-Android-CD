package com.ramirez.campusgps.data

import com.ramirez.campusgps.model.Posicion

interface UbicacionRepository {
    fun playServicesDisponible(): Boolean

    suspend fun obtenerPosicion(): Result<Posicion>
}
