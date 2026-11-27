package com.ramirez.avisoscampus

import android.app.Notification
import android.app.NotificationManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ramirez.avisoscampus.model.Aviso
import com.ramirez.avisoscampus.model.Origen
import com.ramirez.avisoscampus.notificacion.Notificador
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MejorasNotificacionTest {
    private val contexto = InstrumentationRegistry.getInstrumentation().targetContext
    private val manager = contexto.getSystemService(NotificationManager::class.java)

    @Test
    fun conservaTextoCompletoEnNotificacionExpandible() {
        assertTrue("Conceder permiso de notificaciones antes de probar", Notificador.tienePermiso(contexto))
        Notificador.crearCanal(contexto)
        val texto = "Tu entrega fue registrada.\n" +
            "Revisa las observaciones del laboratorio y confirma que adjuntaste todas las evidencias.\n" +
            "Este es un mensaje ficticio para comprobar el texto completo al expandir la notificación."
        val aviso = Aviso(900_001, "Entrega de prueba", texto, Origen.BOTON, "12:00:00")
        try {
            assertTrue(Notificador.mostrar(contexto, aviso))
            val publicada = notificacionPublicada(aviso.id)
            assertEquals(texto, publicada.extras.getCharSequence(Notification.EXTRA_BIG_TEXT).toString())
            assertEquals(texto, publicada.extras.getCharSequence(Notification.EXTRA_TEXT).toString())
        } finally {
            manager.cancel(aviso.id)
        }
    }

    private fun notificacionPublicada(id: Int): Notification {
        repeat(100) {
            manager.activeNotifications.find { it.id == id }?.let { return it.notification }
            Thread.sleep(50)
        }
        throw AssertionError("No llegó la notificación $id")
    }
}
