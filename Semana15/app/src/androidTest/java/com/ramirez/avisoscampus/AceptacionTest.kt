package com.ramirez.avisoscampus

import android.app.Notification
import android.app.NotificationManager
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ramirez.avisoscampus.model.Origen
import com.ramirez.avisoscampus.notificacion.Notificador
import com.ramirez.avisoscampus.viewmodel.AvisosViewModel
import java.io.File
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Ejecutar en un emulador dedicado: las pruebas cambian temporalmente su modo avión. */
@RunWith(AndroidJUnit4::class)
class AceptacionConPermisoTest {
    @get:Rule val regla = createAndroidComposeRule<MainActivity>()

    @Test
    fun avisosRecordatorioReceptorEHistorial() {
        val ayuda = AyudaAceptacion()
        lateinit var vm: AvisosViewModel
        regla.runOnIdle { vm = ViewModelProvider(regla.activity)[AvisosViewModel::class.java] }
        assertTrue("Conceder POST_NOTIFICATIONS antes de esta prueba", Notificador.tienePermiso(ayuda.contexto))
        val manager = ayuda.contexto.getSystemService(NotificationManager::class.java)
        // Cada escenario parte sin notificaciones de otras pruebas de esta misma app.
        manager.cancelAll()
        assertEquals("Avisos", manager.getNotificationChannel(Notificador.CANAL).name.toString())

        // El permiso ya concedido debe limpiar un mensaje previo sin mostrar otro diálogo.
        regla.runOnIdle { vm.noSePudoMostrar() }
        regla.onNodeWithText("Permitir notificaciones").performScrollTo().performClick()
        regla.waitUntil { vm.mensaje.value == null }
        regla.onNodeWithText("Permitido").assertIsDisplayed()

        regla.onNodeWithText("Mostrar aviso").performScrollTo().performClick()
        regla.onNodeWithText("Mostrar aviso").performClick()
        regla.waitUntil(5_000) {
            manager.activeNotifications.count {
                it.id in 1..2 && it.notification.extras.getString(Notification.EXTRA_TITLE) == "Tarea lista"
            } == 2
        }
        assertEquals(2, vm.avisos.value.size)
        assertEquals(Origen.BOTON, vm.avisos.value.first().origen)
        assertEquals("Tarea lista", manager.activeNotifications.first { it.id == 2 }.notification.extras.getString(Notification.EXTRA_TITLE))
        ayuda.shell("cmd statusbar expand-notifications")
        Thread.sleep(2_000)
        ayuda.captura("01-notificacion")
        ayuda.shell("cmd statusbar collapse")

        // La recreación de la Activity conserva el historial y el mismo ViewModel.
        regla.activityRule.scenario.recreate()
        regla.runOnIdle {
            assertSame(vm, ViewModelProvider(regla.activity)[AvisosViewModel::class.java])
            assertEquals(2, vm.avisos.value.size)
        }

        regla.onNodeWithText("Recordar en 10 s").performScrollTo().performClick()
        regla.waitUntil { vm.faltan.value != null }
        regla.onNodeWithText("Recordar en 10 s").assertIsNotEnabled()
        regla.onNodeWithText("Cancelar").assertIsEnabled()
        ayuda.captura("02-recordatorio")
        regla.onNodeWithText("Cancelar").performScrollTo().performClick()
        regla.waitUntil { vm.faltan.value == null }
        Thread.sleep(10_500)
        assertEquals("Cancelar no debe registrar ningún aviso", 2, vm.avisos.value.size)

        regla.onNodeWithText("Recordar en 10 s").performScrollTo().performClick()
        regla.waitUntil { vm.faltan.value != null }
        regla.runOnIdle { regla.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
        regla.waitUntil(5_000) { regla.activity.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE }
        regla.runOnIdle {
            assertSame(vm, ViewModelProvider(regla.activity)[AvisosViewModel::class.java])
            assertNotNull(vm.faltan.value)
        }
        regla.runOnIdle { regla.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
        regla.waitUntil(5_000) { regla.activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT }
        // moveTaskToBack conserva el proceso: el recordatorio debe llegar en segundo plano.
        regla.runOnIdle { regla.activity.moveTaskToBack(true) }
        regla.waitUntil(15_000) { vm.avisos.value.any { it.origen == Origen.RECORDATORIO } }
        regla.waitUntil { vm.faltan.value == null }
        assertEquals("Recordatorio", manager.activeNotifications.first { it.id == 3 }.notification.extras.getString(Notification.EXTRA_TITLE))
        ayuda.shell("am start --activity-reorder-to-front -n ${ayuda.contexto.packageName}/.MainActivity")
        regla.waitForIdle()

        val modoOriginal = ayuda.shell("settings get global airplane_mode_on").trim() == "1"
        try {
            ayuda.shell("cmd connectivity airplane-mode disable")
            regla.onNodeWithTag("tab_receptores").performClick()
            regla.onNodeWithContentDescription("Escuchar el modo avión").performScrollTo().performClick()
            regla.waitUntil { vm.escuchandoAvion.value }
            regla.waitForIdle()
            ayuda.shell("cmd connectivity airplane-mode enable")
            regla.waitUntil(5_000) { vm.avisos.value.any { it.origen == Origen.MODO_AVION && it.texto == "Activado" } }
            regla.onNodeWithTag("tab_historial").performClick()
            regla.onNodeWithText("Activado").performScrollTo().assertIsDisplayed()
            regla.onNodeWithText("Revisa tu laboratorio pendiente").performScrollTo()
            ayuda.captura("03-modo-avion")
            ayuda.shell("cmd connectivity airplane-mode disable")
            regla.waitUntil(5_000) { vm.avisos.value.first().texto == "Desactivado" }

            regla.activityRule.scenario.recreate()
            regla.waitUntil { vm.escuchandoAvion.value }
            ayuda.shell("cmd connectivity airplane-mode enable")
            regla.waitUntil(5_000) { vm.avisos.value.first().texto == "Activado" }

            regla.onNodeWithTag("tab_receptores").performClick()
            regla.onNodeWithContentDescription("Escuchar el modo avión").performScrollTo().performClick()
            regla.waitUntil { !vm.escuchandoAvion.value }
            regla.waitForIdle()
            val cantidad = vm.avisos.value.size
            ayuda.shell("cmd connectivity airplane-mode disable")
            Thread.sleep(1_000)
            assertEquals("Con el switch apagado no debe llegar otro aviso", cantidad, vm.avisos.value.size)
            regla.onNodeWithTag("tab_historial").performClick()
            regla.onAllNodesWithText("Tu entrega fue registrada")[1].performScrollTo().assertIsDisplayed()
            ayuda.captura("04-historial")
        } finally {
            regla.runOnIdle { vm.cambiarEscucha(false) }
            ayuda.shell("cmd connectivity airplane-mode ${if (modoOriginal) "enable" else "disable"}")
        }

        // Dejar un único aviso para comprobar, desde ADB, el toque con el proceso terminado.
        manager.cancelAll()
        regla.runOnIdle { vm.avisarAhora() }
        regla.waitUntil(5_000) { manager.activeNotifications.count { it.notification.extras.getString(Notification.EXTRA_TITLE) == "Tarea lista" } == 1 }
    }
}

@RunWith(AndroidJUnit4::class)
class AceptacionSinPermisoTest {
    @get:Rule val regla = createAndroidComposeRule<MainActivity>()

    @Test
    fun sinPermisoGuardaAvisoYMuestraError() {
        val ayuda = AyudaAceptacion()
        assertFalse("Revocar POST_NOTIFICATIONS antes de esta prueba", Notificador.tienePermiso(ayuda.contexto))
        lateinit var vm: AvisosViewModel
        regla.runOnIdle { vm = ViewModelProvider(regla.activity)[AvisosViewModel::class.java] }
        // Con user-fixed, Android devuelve denegado directamente al launcher.
        regla.onNodeWithText("Permitir notificaciones").performScrollTo().performClick()
        regla.waitUntil { vm.permiso.value == com.ramirez.avisoscampus.model.PermisoUi.Denegado }
        regla.onNodeWithText("Mostrar aviso").performScrollTo().performClick()
        regla.waitUntil { vm.mensaje.value != null }
        assertEquals(1, vm.avisos.value.size)
        assertEquals("Tarea lista", vm.avisos.value.first().titulo)
        regla.onNodeWithText("Denegado").performScrollTo().assertIsDisplayed()
        regla.onNodeWithText("Sin permiso: el aviso se guardó en el historial, pero no se mostró").assertIsDisplayed()
        ayuda.captura("05-permiso-denegado")
        regla.onNodeWithText("Abrir ajustes").performScrollTo().performClick()
        regla.waitUntil(10_000) { ayuda.automatizacion.rootInActiveWindow?.packageName?.toString() == "com.android.settings" }
        assertEquals("com.android.settings", ayuda.automatizacion.rootInActiveWindow.packageName.toString())
    }
}

private class AyudaAceptacion {
    private val instrumentacion = InstrumentationRegistry.getInstrumentation()
    val contexto = instrumentacion.targetContext
    val automatizacion = instrumentacion.uiAutomation

    fun shell(comando: String): String =
        ParcelFileDescriptor.AutoCloseInputStream(automatizacion.executeShellCommand(comando))
            .bufferedReader().use { it.readText() }

    fun captura(nombre: String) {
        instrumentacion.waitForIdleSync()
        Thread.sleep(600)
        val carpeta = File(contexto.getExternalFilesDir(null), "capturas").apply { mkdirs() }
        val bitmap = checkNotNull(automatizacion.takeScreenshot())
        File(carpeta, "$nombre.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        bitmap.recycle()
    }
}
