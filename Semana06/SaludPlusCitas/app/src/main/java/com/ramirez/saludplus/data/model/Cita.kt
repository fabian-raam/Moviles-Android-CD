package com.ramirez.saludplus.data.model

data class Cita(
    val id: Int,
    //No hay Fk entonces solo se conecta con el id
    //Como si crearamos nuestro propio FK pero
    //No abra una BD que valide que es un id existente
    val usuarioId: Int,
    val medicoId: Int,
    val especialidadId: Int,
    val fecha: String,
    val hora: String
)