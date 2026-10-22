package com.ramirez.lavacampus.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.lavacampus.data.local.entity.Usuario
import com.ramirez.lavacampus.data.prefs.PreferenciasStore
import com.ramirez.lavacampus.data.repository.LavaRepository
import com.ramirez.lavacampus.model.DatoGrafico
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SesionViewModel(
    private val repo: LavaRepository,
    private val prefs: PreferenciasStore
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val usuarioActual: StateFlow<Usuario?> = prefs.usuarioId
        .flatMapLatest { id ->
            if (id > 0) repo.observarUsuario(id) else flowOf(null)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val gastoMensual: StateFlow<List<DatoGrafico>> = usuarioActual
        .flatMapLatest { u ->
            if (u != null) {
                repo.observarGastoMensual(u.id).map { lista ->
                    val meses = mapOf(
                        "01" to "Ene", "02" to "Feb", "03" to "Mar", "04" to "Abr",
                        "05" to "May", "06" to "Jun", "07" to "Jul", "08" to "Ago",
                        "09" to "Set", "10" to "Oct", "11" to "Nov", "12" to "Dic"
                    )
                    if (lista.isEmpty()) {
                        listOf(
                            DatoGrafico("Ene", 0.0, Color(0xFF0891B2)),
                            DatoGrafico("Feb", 0.0, Color(0xFF0891B2)),
                            DatoGrafico("Mar", 0.0, Color(0xFF0891B2)),
                            DatoGrafico("Abr", 0.0, Color(0xFF0891B2))
                        )
                    } else {
                        lista.map { g ->
                            DatoGrafico(
                                etiqueta = meses[g.mes] ?: g.mes,
                                valor = g.total,
                                color = Color(0xFF0891B2)
                            )
                        }
                    }
                }
            } else {
                flowOf(emptyList())
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val modoOscuro: StateFlow<Boolean> = prefs.modoOscuro
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val perfilActivo: StateFlow<String> = prefs.perfilActivo
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    fun login(correo: String, clave: String, rolSeleccionado: String, onExito: () -> Unit) {
        if (correo.isBlank() || clave.isBlank()) {
            _error.value = "Por favor completa todos los campos"
            return
        }

        viewModelScope.launch {
            val usuario = repo.login(correo.trim(), clave.trim())
            if (usuario == null) {
                _error.value = "Correo o contraseña incorrectos"
            } else if (usuario.rol != rolSeleccionado) {
                _error.value = "El rol seleccionado no corresponde a estas credenciales"
            } else {
                _error.value = null
                prefs.guardarSesion(usuario.rol, usuario.id)
                onExito()
            }
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            prefs.cerrarSesion()
        }
    }

    fun limpiarError() {
        _error.value = null
    }
}
