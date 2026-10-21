package com.ramirez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.lavacampus.data.local.entity.Servicio
import com.ramirez.lavacampus.data.repository.LavaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ServiciosViewModel(private val repo: LavaRepository) : ViewModel() {

    val servicios: StateFlow<List<Servicio>> = repo.observarServicios()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun alternar(s: Servicio) {
        viewModelScope.launch {
            repo.cambiarActivo(s.id, !s.activo)
        }
    }

    fun guardar(s: Servicio, onExito: () -> Unit) {
        viewModelScope.launch {
            repo.guardarServicio(s)
            _error.value = null
            onExito()
        }
    }

    fun eliminar(s: Servicio, onExito: () -> Unit) {
        viewModelScope.launch {
            if (repo.servicioTieneDetalles(s.id)) {
                _error.value = "Un servicio que ya está registrado en algún pedido no se puede eliminar. Desactívalo en su lugar."
            } else {
                repo.eliminarServicio(s)
                _error.value = null
                onExito()
            }
        }
    }

    fun limpiarError() {
        _error.value = null
    }
}