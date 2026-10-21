package com.ramirez.lavacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramirez.lavacampus.data.prefs.PreferenciasStore
import com.ramirez.lavacampus.data.repository.LavaRepository

class LavaViewModelFactory(
    private val repo: LavaRepository,
    private val prefs: PreferenciasStore
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SesionViewModel::class.java) -> {
                SesionViewModel(repo, prefs) as T
            }
            // Agregaremos los demás ViewModels a medida que los construyamos
            else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
        }
    }
}