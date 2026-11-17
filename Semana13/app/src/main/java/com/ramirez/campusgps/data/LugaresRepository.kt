package com.ramirez.campusgps.data

import com.ramirez.campusgps.model.Lugar

interface LugaresRepository {
    fun obtenerLugares(): List<Lugar>
}
