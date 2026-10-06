package com.ramirez.saludplus.data.model

data class Medico(
    val id: Int,
    val nombre: String,
    //No hay Fk entonces solo se conecta con el id
    //Como si crearamos nuestro propio FK pero
    //No abra una BD que valide que es un id existente
    val especialidadId: Int
)