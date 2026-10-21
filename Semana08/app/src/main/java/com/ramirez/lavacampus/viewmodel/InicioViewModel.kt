package com.ramirez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.lavacampus.data.local.relation.PedidoConDetalle
import com.ramirez.lavacampus.data.repository.LavaRepository
import com.ramirez.lavacampus.model.EstadoPedido
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class InicioViewModel(
    private val repo: LavaRepository,
    private val usuarioId: Int
) : ViewModel() {

    private val pedidosFlow = repo.observarPedidosDeUsuario(usuarioId)

    // El pedido activo es el más reciente en estado RECIBIDO, EN_PROCESO o LISTO
    val pedidoActivo: StateFlow<PedidoConDetalle?> = pedidosFlow
        .map { lista ->
            lista.firstOrNull { p ->
                val estado = p.pedido.estado
                estado == EstadoPedido.RECIBIDO.name ||
                        estado == EstadoPedido.EN_PROCESO.name ||
                        estado == EstadoPedido.LISTO.name
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // Los 2 últimos pedidos del estudiante
    val recientes: StateFlow<List<PedidoConDetalle>> = pedidosFlow
        .map { lista -> lista.take(2) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}