package com.ramirez.avisoscampus

import android.graphics.Bitmap
import android.os.ParcelFileDescriptor
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ramirez.avisoscampus.model.Origen
import com.ramirez.avisoscampus.viewmodel.AvisosViewModel
import java.io.File
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DisenoNavegacionTest {
    @get:Rule val regla = createAndroidComposeRule<MainActivity>()

    @Test
    fun conservaEstadoEntreSeccionesYAtrásVuelveAAvisos() {
        lateinit var vm: AvisosViewModel
        regla.runOnIdle { vm = ViewModelProvider(regla.activity)[AvisosViewModel::class.java] }
        regla.onNodeWithTag("tab_avisos").assertIsSelected()
        captura("01-avisos")
        regla.onNodeWithText("Mostrar aviso").performScrollTo().performClick()
        regla.waitUntil { vm.avisos.value.size == 1 }
        regla.onNodeWithTag("tab_receptores").performClick()
        regla.onNodeWithTag("tab_receptores").assertIsSelected()
        regla.onNodeWithContentDescription("Escuchar el modo avión").performScrollTo().performClick()
        regla.onNodeWithContentDescription("Escuchar el cargador").performScrollTo().performClick()
        regla.waitForIdle()
        captura("02-receptores")
        val modoOriginal = shell("settings get global airplane_mode_on").trim() == "1"
        try {
            regla.onNodeWithTag("tab_historial").performClick()
            shell("cmd connectivity airplane-mode ${if (modoOriginal) "disable" else "enable"}")
            regla.waitUntil(5_000) { vm.avisos.value.any { it.origen == Origen.MODO_AVION } }
            regla.onNodeWithTag("tab_historial").assertIsSelected()
            regla.onAllNodesWithText("Tu entrega fue registrada").onFirst().performScrollTo().assertIsDisplayed()
            captura("03-historial")
            regla.activityRule.scenario.recreate()
            regla.onNodeWithTag("tab_historial").assertIsSelected()
            regla.runOnIdle {
                assertTrue(vm.escuchandoAvion.value)
                assertTrue(vm.escuchandoCargador.value)
                assertSame(vm, ViewModelProvider(regla.activity)[AvisosViewModel::class.java])
            }
            // Atrás desde una sección secundaria lleva a Avisos, sin cerrar la Activity.
            Espresso.pressBack()
            regla.onNodeWithTag("tab_avisos").assertIsSelected()
            regla.onNodeWithText("Recordar en 10 s").performScrollTo().performClick()
            regla.waitUntil { vm.faltan.value != null }
            regla.waitForIdle()
            val recordatorioY = regla.onNodeWithText("Recordatorio").fetchSemanticsNode().boundsInRoot.top
            val avisoY = regla.onNodeWithText("Mostrar aviso").fetchSemanticsNode().boundsInRoot.top
            assertTrue("El recordatorio activo aparece primero", recordatorioY < avisoY)
            captura("04-recordatorio-activo")
            regla.onNodeWithTag("tab_receptores").performClick()
            regla.onNodeWithContentDescription("Escuchar el modo avión").assertIsOn()
            regla.onNodeWithContentDescription("Escuchar el cargador").assertIsOn()
            regla.onNodeWithTag("tab_historial").performClick()
            regla.waitUntil(15_000) { vm.avisos.value.any { it.origen == Origen.RECORDATORIO } }
            regla.onNodeWithTag("tab_avisos").performClick()
            regla.onNodeWithText("Recordar en 10 s").performScrollTo().assertIsEnabled()
        } finally {
            regla.runOnIdle {
                vm.cancelarRecordatorio()
                vm.cambiarEscucha(false)
                vm.cambiarEscuchaCargador(false)
            }
            regla.waitForIdle()
            shell("cmd connectivity airplane-mode ${if (modoOriginal) "enable" else "disable"}")
        }
    }

    private fun captura(nombre: String) {
        regla.waitForIdle()
        Thread.sleep(500)
        val instrumentacion = InstrumentationRegistry.getInstrumentation()
        val carpeta = File(instrumentacion.targetContext.getExternalFilesDir(null), "rediseno").apply { mkdirs() }
        val bitmap = checkNotNull(instrumentacion.uiAutomation.takeScreenshot())
        File(carpeta, "$nombre.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        bitmap.recycle()
    }

    private fun shell(comando: String): String =
        ParcelFileDescriptor.AutoCloseInputStream(
            InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand(comando)
        ).bufferedReader().use { it.readText() }
}
