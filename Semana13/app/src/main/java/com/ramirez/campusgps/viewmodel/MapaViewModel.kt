package com.ramirez.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.campusgps.data.LugaresRepository
import com.ramirez.campusgps.data.UbicacionRepository
import com.ramirez.campusgps.model.LugarConDistancia
import com.ramirez.campusgps.model.Lugar
import com.ramirez.campusgps.model.Posicion
import com.ramirez.campusgps.model.UbicacionUi
import com.ramirez.campusgps.model.TipoError
import com.ramirez.campusgps.model.UbicacionNoDisponibleException
import com.ramirez.campusgps.util.distanciaMetros
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MapaViewModel(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository
) : ViewModel() {
    //encarga de obtener las ubicaciones para construir las pantallas
    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado: StateFlow<UbicacionUi> = _estado.asStateFlow()

    private val _lugares = MutableStateFlow(
        lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) }
    )
    // FILTRO POR CATEGORIA: obtiene las categorias de los lugares sin repetirlas.
    val categorias: List<String> = _lugares.value.map { it.lugar.categoria }.distinct()
    private val _categoriaSeleccionada = MutableStateFlow<String?>(null)
    val categoriaSeleccionada: StateFlow<String?> = _categoriaSeleccionada.asStateFlow()
    val lugares: StateFlow<List<LugarConDistancia>> = combine(
        _lugares,
        _categoriaSeleccionada
    ) { lista, categoria ->
        // Con "Todas" pasan todos; en otro caso pasan los de la categoria elegida.
        lista.filter { categoria == null || it.lugar.categoria == categoria }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _lugares.value)

    // RADIO DE CERCANIA: guarda el radio elegido en metros.
    val radiosDisponibles: List<Int> = listOf(100, 200, 500)
    private val _radioMetros = MutableStateFlow(100)
    val radioMetros: StateFlow<Int> = _radioMetros.asStateFlow()

    // Actualiza los lugares dentro del radio cuando cambian las distancias o el radio.
    val lugaresDentroDelRadio: StateFlow<Set<Int>> = combine(
        lugares,
        _radioMetros
    ) { lista, radio ->
        lista.filter { elemento ->
            elemento.metros?.let { it <= radio } == true
        }.map { it.lugar.id }.toSet()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val masCercano: StateFlow<Lugar?> = lugares
        .map { lista -> lista.firstOrNull { it.metros != null }?.lugar }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _seleccionado = MutableStateFlow<Int?>(null)
    val seleccionado: StateFlow<Int?> = _seleccionado.asStateFlow()

    fun buscar() {
        if (_estado.value is UbicacionUi.Buscando) return

        if (!ubicacionRepo.playServicesDisponible()) {
            _estado.value = UbicacionUi.Error(
                mensaje = "Este dispositivo no tiene Google Play Services",
                tipo = TipoError.SIN_PLAY_SERVICES
            )
            return
        }

        _estado.value = UbicacionUi.Buscando
        viewModelScope.launch {
            ubicacionRepo.obtenerPosicion()
                .onSuccess { posicion ->
                    _estado.value = UbicacionUi.Lista(posicion)
                    actualizarDistancias(posicion)
                }
                .onFailure { error ->
                    _estado.value = errorDe(error)
                }
        }
    }

    private fun actualizarDistancias(posicion: Posicion) {
        _lugares.value = lugaresRepo.obtenerLugares()
            .map { lugar ->
                LugarConDistancia(
                    lugar = lugar,
                    metros = distanciaMetros(posicion, lugar)
                )
            }
            .sortedBy { it.metros }
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error(
            mensaje = "Sin permiso de ubicación no puedo mostrar dónde estás",
            tipo = TipoError.SIN_PERMISO
        )
    }

    fun seleccionar(id: Int?) {
        _seleccionado.value = id
    }

    fun seleccionarRadio(metros: Int) {
        if (metros in radiosDisponibles) {
            _radioMetros.value = metros
        }
    }

    // FILTRO POR CATEGORIA: la pantalla llama esta funcion al pulsar un chip.
    fun filtrarPorCategoria(categoria: String?) {
        // Acepta "Todas" (null) o una categoria existente.
        if (categoria != null && categoria !in categorias) return

        // Este cambio hace que combine publique la nueva lista filtrada.
        _categoriaSeleccionada.value = categoria
        val lugarSeleccionado = _lugares.value.firstOrNull {
            it.lugar.id == _seleccionado.value
        }?.lugar
        // Quita la seleccion si el lugar queda oculto por el filtro.
        if (categoria != null && lugarSeleccionado?.categoria != categoria) {
            _seleccionado.value = null
        }
    }

    private fun errorDe(error: Throwable): UbicacionUi.Error = when (error) {
        is SecurityException -> UbicacionUi.Error(
            mensaje = "Falta el permiso de ubicación",
            tipo = TipoError.SIN_PERMISO
        )
        is UbicacionNoDisponibleException -> UbicacionUi.Error(
            mensaje = "Activa el GPS e intenta de nuevo",
            tipo = TipoError.GPS_APAGADO
        )
        else -> UbicacionUi.Error(
            mensaje = error.message ?: "No se pudo obtener la ubicación",
            tipo = TipoError.OTRO
        )
    }
}
