package com.ramirez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.lavacampus.data.local.relation.PedidoConDetalle
import com.ramirez.lavacampus.data.repository.LavaRepository
import com.ramirez.lavacampus.model.EstadoPedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GestionPedidoViewModel(
    private val repo: LavaRepository,
    private val pedidoId: Int
) : ViewModel() {

    val pedido: StateFlow<PedidoConDetalle?> = repo.observarPedido(pedidoId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val casillerosOcupados: StateFlow<List<String>> = repo.observarCasillerosOcupados()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _casilleroElegido = MutableStateFlow<String?>(null)
    val casilleroElegido: StateFlow<String?> = _casilleroElegido.asStateFlow()

    private val _pagado = MutableStateFlow(false)
    val pagado: StateFlow<Boolean> = _pagado.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun elegirCasillero(c: String) {
        _casilleroElegido.value = c
        _error.value = null
    }

    fun cambiarPagado(b: Boolean) {
        _pagado.value = b
        _error.value = null
        viewModelScope.launch {
            repo.marcarPagado(pedidoId, b)
        }
    }

    fun avanzarEstado(onExito: () -> Unit) {
        val p = pedido.value?.pedido ?: return
        val estadoActual = try {
            EstadoPedido.valueOf(p.estado)
        } catch (e: Exception) {
            EstadoPedido.RECIBIDO
        }

        val siguiente = estadoActual.siguiente() ?: return

        // Regla: Para pasar a LISTO exige elegir un casillero
        if (siguiente == EstadoPedido.LISTO) {
            val c = casilleroElegido.value ?: p.casillero
            if (c.isNullOrEmpty()) {
                _error.value = "Debes seleccionar un casillero disponible para marcarlo como LISTO"
                return
            }
        }

        // Regla: Para pasar a ENTREGADO exige cobro previo
        if (siguiente == EstadoPedido.ENTREGADO && !p.pagado && !pagado.value) {
            _error.value = "Debes marcar el pedido como COBRADO antes de poder entregarlo"
            return
        }

        viewModelScope.launch {
            val casilleroFinal = if (siguiente == EstadoPedido.LISTO) {
                casilleroElegido.value ?: p.casillero
            } else {
                p.casillero
            }

            repo.cambiarEstado(pedidoId, siguiente, casilleroFinal)
            _error.value = null
            onExito()
        }
    }

    fun cancelarPedido(onExito: () -> Unit) {
        val p = pedido.value?.pedido ?: return
        if (p.estado == EstadoPedido.LISTO.name || p.estado == EstadoPedido.ENTREGADO.name) {
            _error.value = "No se puede cancelar un pedido que ya está LISTO o ENTREGADO"
            return
        }

        viewModelScope.launch {
            repo.cambiarEstado(pedidoId, EstadoPedido.CANCELADO, null)
            onExito()
        }
    }
}