package com.ramirez.campusgps.model

// metros es null mientras no se conoce la ubicacion del usuario.
data class LugarConDistancia(
    val lugar: Lugar,
    val metros: Float?
)
