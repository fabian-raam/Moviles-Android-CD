package com.ramirez.avisoscampus

import android.app.Notification
import android.app.NotificationManager
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ramirez.avisoscampus.model.Origen
import com.ramirez.avisoscampus.notificacion.Notificador
import com.ramirez.avisoscampus.viewmodel.AvisosViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Ejecutar en un emulador dedicado: se simula la alimentación y se restaura en finally. */
@RunWith(AndroidJUnit4::class)
class CargadorTest {
    @get:Rule val regla = createAndroidComposeRule<MainActivity>()

    @Test
    fun escuchaSoloConSwitchActivoYConservaEstadoAlRecrear() {
        val instrumentacion = InstrumentationRegistry.getInstrumentation()
        val contexto = instrumentacion.targetContext
        val manager = contexto.getSystemService(NotificationManager::class.java)
        assertTrue(Notificador.tienePermiso(contexto))
        lateinit var vm: AvisosViewModel
        regla.runOnIdle { vm = ViewModelProvider(regla.activity)[AvisosViewModel::class.java] }
        assertFalse(vm.escuchandoCargador.value)
        try {
            shell("dumpsys battery unplug")
            regla.onNodeWithContentDescription("Escuchar el cargador").performScrollTo().performClick()
            regla.waitUntil(5_000) { vm.escuchandoCargador.value }
            regla.waitForIdle()
            val inicial = vm.avisos.value.size

            shell("dumpsys battery set ac 1")
            regla.waitUntil(5_000) {
                vm.avisos.value.firstOrNull()?.let { it.origen == Origen.CARGADOR && it.texto == "Conectado" } == true
            }
            assertEquals(inicial + 1, vm.avisos.value.size)
            val id = vm.avisos.value.first().id
            regla.waitUntil(5_000) {
                manager.activeNotifications.any {
                    it.id == id && it.notification.channelId == Notificador.CANAL &&
                        it.notification.extras.getString(Notification.EXTRA_TITLE) == "Cargador"
                }
            }

            regla.activityRule.scenario.recreate()
            regla.runOnIdle { assertSame(vm, ViewModelProvider(regla.activity)[AvisosViewModel::class.java]) }
            regla.onNodeWithContentDescription("Escuchar el cargador").performScrollTo().assertIsOn()
            shell("dumpsys battery set ac 0")
            regla.waitUntil(5_000) {
                vm.avisos.value.firstOrNull()?.let { it.origen == Origen.CARGADOR && it.texto == "Desconectado" } == true
            }
            assertEquals("Un solo evento tras recrear: no debe duplicarse el receptor", inicial + 2, vm.avisos.value.size)

            regla.onNodeWithContentDescription("Escuchar el cargador").performScrollTo().performClick()
            regla.waitUntil(5_000) { !vm.escuchandoCargador.value }
            regla.waitForIdle()
            val cantidad = vm.avisos.value.size
            shell("dumpsys battery set ac 1")
            Thread.sleep(1_000)
            assertEquals("Con el switch apagado no se debe registrar otro aviso", cantidad, vm.avisos.value.size)
        } finally {
            try {
                regla.runOnIdle { vm.cambiarEscuchaCargador(false) }
                regla.waitForIdle()
            } finally {
                shell("dumpsys battery reset")
                vm.avisos.value.filter { it.origen == Origen.CARGADOR }.forEach { manager.cancel(it.id) }
            }
        }
    }

    private fun shell(comando: String): String =
        ParcelFileDescriptor.AutoCloseInputStream(
            InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand(comando)
        ).bufferedReader().use { it.readText() }
}
