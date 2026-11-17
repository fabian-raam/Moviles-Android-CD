package com.ramirez.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.campusgps.data.LugaresRepository
import com.ramirez.campusgps.data.UbicacionRepository
import com.ramirez.campusgps.model.LugarConDistancia
import com.ramirez.campusgps.model.UbicacionUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val lugares: StateFlow<List<LugarConDistancia>> = _lugares.asStateFlow()

    private val _seleccionado = MutableStateFlow<Int?>(null)
    val seleccionado: StateFlow<Int?> = _seleccionado.asStateFlow()

    fun buscar() {
        if (_estado.value is UbicacionUi.Buscando) return

        _estado.value = UbicacionUi.Buscando
        viewModelScope.launch {
            ubicacionRepo.obtenerPosicion()
                .onSuccess { posicion ->
                    _estado.value = UbicacionUi.Lista(posicion)
                    // TODO Paso 5: actualizarDistancias(posicion).
                }
                .onFailure { error ->
                    _estado.value = UbicacionUi.Error(mensajeDe(error))
                }
        }
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error(
            "Sin permiso de ubicación no puedo mostrar dónde estás"
        )
    }

    // TODO Paso 6: agregar seleccionar(id: Int?).
    // TODO Paso 7: derivar masCercano a partir de lugares.
    // TODO Paso 8: consultar Play Services y clasificar los errores.

    private fun mensajeDe(error: Throwable): String = when (error) {
        is SecurityException -> "Falta el permiso de ubicación"
        else -> error.message ?: "No se pudo obtener la ubicación"
    }
}
