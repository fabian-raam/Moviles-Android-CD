package com.ramirez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.lavacampus.data.local.entity.Usuario
import com.ramirez.lavacampus.data.prefs.PreferenciasStore
import com.ramirez.lavacampus.data.repository.LavaRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
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
