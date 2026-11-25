package com.ramirez.murocampus.data

import java.io.IOException
import kotlinx.serialization.SerializationException
import retrofit2.HttpException

// Se mantiene en data para no introducir dependencias de Retrofit en el ViewModel.
fun mensajeDe(error: Throwable): String = when (error) {
    is IOException -> "Sin conexión a internet.\nRevisa tu red e inténtalo otra vez."
    is HttpException -> "Error del servidor (${error.code()})"
    is SerializationException -> "Respuesta inesperada"
    else -> "Error desconocido"
}
