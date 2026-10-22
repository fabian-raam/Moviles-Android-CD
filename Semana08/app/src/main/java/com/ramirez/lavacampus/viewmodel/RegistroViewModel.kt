package com.ramirez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.lavacampus.data.prefs.PreferenciasStore
import com.ramirez.lavacampus.data.repository.LavaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistroViewModel(
    private val repo: LavaRepository,
    private val prefs: PreferenciasStore
) : ViewModel() {

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _codigo = MutableStateFlow("")
    val codigo: StateFlow<String> = _codigo.asStateFlow()

    private val _correo = MutableStateFlow("")
    val correo: StateFlow<String> = _correo.asStateFlow()

    private val _clave = MutableStateFlow("")
    val clave: StateFlow<String> = _clave.asStateFlow()

    private val _confirmarClave = MutableStateFlow("")
    val confirmarClave: StateFlow<String> = _confirmarClave.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun cambiarNombre(v: String) { _nombre.value = v; _error.value = null }
    fun cambiarCodigo(v: String) { _codigo.value = v; _error.value = null }
    fun cambiarCorreo(v: String) { _correo.value = v; _error.value = null }
    fun cambiarClave(v: String) { _clave.value = v; _error.value = null }
    fun cambiarConfirmarClave(v: String) { _confirmarClave.value = v; _error.value = null }

    fun registrar(onExito: () -> Unit) {
        val nom = _nombre.value.trim()
        val cod = _codigo.value.trim()
        val cor = _correo.value.trim()
        val cla = _clave.value.trim()
        val conf = _confirmarClave.value.trim()

        if (nom.isBlank() || cod.isBlank() || cor.isBlank() || cla.isBlank()) {
            _error.value = "Por favor completa todos los campos"
            return
        }

        // Regla 1: El nombre no debe contener números
        if (nom.any { it.isDigit() }) {
            _error.value = "El nombre no puede contener números"
            return
        }

        // Regla 2: El código de estudiante debe ser de exactamente 6 dígitos numéricos
        if (cod.length != 6 || !cod.all { it.isDigit() }) {
            _error.value = "El código debe tener exactamente 6 dígitos numéricos"
            return
        }

        // Regla 3: Validar formato de correo electrónico
        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        if (!emailRegex.matches(cor)) {
            _error.value = "Ingresa un correo electrónico válido (ej. alumno@correo.com)"
            return
        }

        // Regla 4: Contraseña mínimo de 5 dígitos
        if (cla.length < 5) {
            _error.value = "La contraseña debe tener al menos 5 dígitos"
            return
        }

        if (cla != conf) {
            _error.value = "Las contraseñas no coinciden"
            return
        }

        viewModelScope.launch {
            val idCreado = repo.registrarEstudiante(nom, cod, cor, cla)
            if (idCreado > 0) {
                _error.value = null
                onExito()
            } else {
                _error.value = "No se pudo registrar la cuenta de estudiante"
            }
        }
    }
}
