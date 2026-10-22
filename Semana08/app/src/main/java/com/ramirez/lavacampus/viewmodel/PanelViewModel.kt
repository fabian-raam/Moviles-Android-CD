package com.ramirez.lavacampus.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.lavacampus.data.repository.LavaRepository
import com.ramirez.lavacampus.model.DatoGrafico
import com.ramirez.lavacampus.model.EstadoPedido
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.DayOfWeek
import java.time.LocalDate

class PanelViewModel(private val repo: LavaRepository) : ViewModel() {

    private val hoy = LocalDate.now().toString()
    private val lunesSemana = LocalDate.now().with(DayOfWeek.MONDAY).toString()

    // Contadores
    val pedidosHoy: StateFlow<Int> = repo.observarPedidosDeFecha(hoy)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val enProceso: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.EN_PROCESO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val porRecoger: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.LISTO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val ingresosSemana: StateFlow<Double> = repo.observarIngresosDesde(lunesSemana)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val promedioCalificacion: StateFlow<Double> = repo.observarPromedioCalificacion()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // Datos para el Gráfico de Dona por Estado
    val porEstado: StateFlow<List<DatoGrafico>> = combine(
        repo.observarCantidadPorEstado(EstadoPedido.RECIBIDO),
        repo.observarCantidadPorEstado(EstadoPedido.EN_PROCESO),
        repo.observarCantidadPorEstado(EstadoPedido.LISTO),
        repo.observarCantidadPorEstado(EstadoPedido.ENTREGADO)
    ) { recibido, proceso, listo, entregado ->
        listOf(
            DatoGrafico("Recibido", recibido.toDouble(), Color(0xFFD97706)),
            DatoGrafico("En proceso", proceso.toDouble(), Color(0xFF2563EB)),
            DatoGrafico("Listo", listo.toDouble(), Color(0xFF16A34A)),
            DatoGrafico("Entregado", entregado.toDouble(), Color(0xFF475569))
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Datos para el Gráfico de Barras por Servicio
    val porServicio: StateFlow<List<DatoGrafico>> = repo.observarIngresosPorServicio(lunesSemana)
        .map { lista ->
            lista.map { s ->
                DatoGrafico(s.nombre, s.total, Color(0xFF6D28D9))
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}