package com.ramirez.avisoscampus.notificacion

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.ramirez.avisoscampus.model.Aviso

/** Crea el canal de Android; el permiso y los avisos se completan en los próximos pasos. */
@Suppress("UNUSED_PARAMETER")
object Notificador {
    const val CANAL = "avisos"

    fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(CANAL, "Avisos", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(canal)
        }
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
