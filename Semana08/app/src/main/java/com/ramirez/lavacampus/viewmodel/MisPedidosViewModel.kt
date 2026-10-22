package com.ramirez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.lavacampus.data.local.entity.Servicio
import com.ramirez.lavacampus.data.local.relation.PedidoConDetalle
import com.ramirez.lavacampus.data.repository.LavaRepository
import com.ramirez.lavacampus.model.EstadoPedido
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MisPedidosViewModel(
    private val repo: LavaRepository,
    private val usuarioId: Int
) : ViewModel() {

    private val pedidosFlow = repo.observarPedidosDeUsuario(usuarioId)

    val activos: StateFlow<List<PedidoConDetalle>> = pedidosFlow
        .map { lista ->
            lista.filter { p ->
                val estado = p.pedido.estado
                estado == EstadoPedido.RECIBIDO.name ||
                        estado == EstadoPedido.EN_PROCESO.name ||
                        estado == EstadoPedido.LISTO.name
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val historial: StateFlow<List<PedidoConDetalle>> = pedidosFlow
        .map { lista ->
            lista.filter { p ->
                val estado = p.pedido.estado
                estado == EstadoPedido.ENTREGADO.name ||
                        estado == EstadoPedido.CANCELADO.name
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val serviciosActivos: StateFlow<List<Servicio>> = repo.observarServiciosActivos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun cancelarPedido(pedidoId: Int) {
        viewModelScope.launch {
            repo.cambiarEstado(pedidoId, EstadoPedido.CANCELADO, null)
        }
    }

    fun calificarPedido(pedidoId: Int, estrellas: Int) {
        viewModelScope.launch {
            repo.calificarPedido(pedidoId, estrellas)
        }
    }
}