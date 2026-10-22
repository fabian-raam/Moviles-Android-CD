package com.ramirez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.lavacampus.data.local.entity.Servicio
import com.ramirez.lavacampus.data.repository.LavaRepository
import com.ramirez.lavacampus.model.LineaPedido
import com.ramirez.lavacampus.model.Unidad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

class NuevoPedidoViewModel(
    private val repo: LavaRepository,
    private val usuarioId: Int
) : ViewModel() {

    val serviciosActivos: StateFlow<List<Servicio>> = repo.observarServiciosActivos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _cantidades = MutableStateFlow<Map<Int, Double>>(emptyMap())
    val cantidades: StateFlow<Map<Int, Double>> = _cantidades.asStateFlow()

    private val _nota = MutableStateFlow("")
    val nota: StateFlow<String> = _nota.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Cálculo dinámico del subtotal bruto
    val subtotalBruto: StateFlow<Double> = combine(serviciosActivos, cantidades) { servicios, mapCantidades ->
        servicios.sumOf { s ->
            val cant = mapCantidades[s.id] ?: 0.0
            cant * s.precio
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // Cálculo dinámico del descuento por volumen (10% si el lavado en KG supera 5kg)
    val descuento: StateFlow<Double> = combine(serviciosActivos, cantidades) { servicios, mapCantidades ->
        servicios.filter { s -> s.unidad == Unidad.KG.name && (mapCantidades[s.id] ?: 0.0) > 5.0 }
            .sumOf { s ->
                val cant = mapCantidades[s.id] ?: 0.0
                cant * s.precio * 0.10
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // Total neto (Subtotal - Descuento)
    val total: StateFlow<Double> = combine(subtotalBruto, descuento) { bruto, desc ->
        (bruto - desc).coerceAtLeast(0.0)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // Cálculo de la fecha de entrega saltando domingos
    val fechaEntrega: StateFlow<String> = combine(serviciosActivos, cantidades) { servicios, mapCantidades ->
        val horasMax = servicios
            .filter { (mapCantidades[it.id] ?: 0.0) > 0 }
            .maxOfOrNull { it.horasEntrega } ?: 24

        val diasAdicionales = (horasMax / 24).toLong()
        var fecha = LocalDate.now().plusDays(diasAdicionales)

        // Si cae domingo, se pasa al lunes
        if (fecha.dayOfWeek == DayOfWeek.SUNDAY) {
            fecha = fecha.plusDays(1)
        }

        fecha.toString()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LocalDate.now().toString())

    fun cambiarCantidad(servicioId: Int, nuevaCantidad: Double) {
        val mapa = _cantidades.value.toMutableMap()
        if (nuevaCantidad <= 0.0) {
            mapa.remove(servicioId)
        } else {
            mapa[servicioId] = nuevaCantidad
        }
        _cantidades.value = mapa
        _error.value = null
    }

    fun cambiarNota(nuevaNota: String) {
        _nota.value = nuevaNota
    }

    fun confirmarPedido(onExito: () -> Unit) {
        viewModelScope.launch {
            val activos = repo.contarActivos(usuarioId)
            if (activos >= 3) {
                _error.value = "Ya tienes 3 pedidos activos. Recoge o espera que se entreguen para crear otro."
                return@launch
            }

            val servicios = serviciosActivos.value
            val mapa = cantidades.value
            val lineas = servicios.mapNotNull { s ->
                val cant = mapa[s.id] ?: 0.0
                if (cant > 0) LineaPedido(s, cant) else null
            }

            if (lineas.isEmpty()) {
                _error.value = "Selecciona al menos un servicio con cantidad mayor a 0"
                return@launch
            }

            repo.crearPedido(
                usuarioId = usuarioId,
                lineas = lineas,
                fechaEntrega = fechaEntrega.value,
                nota = nota.value.trim(),
                descuento = descuento.value
            )

            onExito()
        }
    }
}