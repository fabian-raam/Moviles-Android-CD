package com.ramirez.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramirez.campusgps.data.LugaresRepository
import com.ramirez.campusgps.data.UbicacionRepository

class CampusViewModelFactory(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository
) : ViewModelProvider.Factory {
    //Creamos una instancia de mapaviewmodel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapaViewModel(ubicacionRepo, lugaresRepo) as T
        }

        throw IllegalArgumentException("ViewModel no reconocido: ${modelClass.name}")
    }
}
