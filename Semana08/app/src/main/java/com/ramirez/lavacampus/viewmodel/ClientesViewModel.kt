package com.ramirez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.lavacampus.data.repository.LavaRepository
import com.ramirez.lavacampus.model.ClienteResumen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ClientesViewModel(private val repo: LavaRepository) : ViewModel() {

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    val clientes: StateFlow<List<ClienteResumen>> = combine(
        repo.observarClientes(),
        _busqueda
    ) { lista, query ->
        val q = query.trim().lowercase()
        if (q.isEmpty()) {
            lista
        } else {
            lista.filter { c ->
                c.nombre.lowercase().contains(q) ||
                        c.codigo.lowercase().contains(q) ||
                        c.correo.lowercase().contains(q)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun cambiarBusqueda(query: String) {
        _busqueda.value = query
    }
}