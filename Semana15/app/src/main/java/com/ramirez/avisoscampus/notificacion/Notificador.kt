package com.ramirez.avisoscampus.notificacion

import android.content.Context
import com.ramirez.avisoscampus.model.Aviso

/** Esqueleto del laboratorio: todavía no interactúa con las notificaciones de Android. */
@Suppress("UNUSED_PARAMETER")
object Notificador {
    const val CANAL = "avisos"

    fun crearCanal(context: Context) {
        // TODO Paso 1: crear y registrar el canal Avisos en Android 8+.
    }

    fun tienePermiso(context: Context): Boolean {
        // TODO Paso 2: comprobar POST_NOTIFICATIONS en API 33+; true antes de API 33.
        return false
    }

    fun mostrar(context: Context, aviso: Aviso): Boolean {
        // TODO Paso 3: comprobar permiso, construir la notificación y mostrarla.
        // TODO Paso 4: agregar el PendingIntent para abrir MainActivity.
        return false
    }
}
