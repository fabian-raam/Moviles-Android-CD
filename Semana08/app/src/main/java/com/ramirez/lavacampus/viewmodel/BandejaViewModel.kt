package com.ramirez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.lavacampus.data.local.relation.PedidoConDetalle
import com.ramirez.lavacampus.data.repository.LavaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class BandejaViewModel(private val repo: LavaRepository) : ViewModel() {

    private val _filtroEstado = MutableStateFlow("TODOS")
    val filtroEstado: StateFlow<String> = _filtroEstado.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    val pedidos: StateFlow<List<PedidoConDetalle>> = combine(
        repo.observarPedidos(),
        _filtroEstado,
        _busqueda
    ) { lista, filtro, query ->
        lista.filter { p ->
            val coincideEstado = if (filtro == "TODOS") true else p.pedido.estado == filtro
            val q = query.trim().lowercase()
            val coincideBusqueda = q.isEmpty() ||
                    p.usuario.nombre.lowercase().contains(q) ||
                    p.pedido.id.toString().contains(q)

            coincideEstado && coincideBusqueda
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun cambiarFiltro(nuevoFiltro: String) {
        _filtroEstado.value = nuevoFiltro
    }

    fun cambiarBusqueda(query: String) {
        _busqueda.value = query
    }
}