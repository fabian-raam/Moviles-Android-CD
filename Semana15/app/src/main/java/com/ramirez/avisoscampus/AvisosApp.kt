package com.ramirez.avisoscampus

import android.app.Application
import com.ramirez.avisoscampus.notificacion.Notificador

class AvisosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Notificador.crearCanal(this)
    }
}
