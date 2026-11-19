package com.ramirez.centrotareas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramirez.centrotareas.data.TareasRepository

class TareasViewModelFactory(
    private val repo: TareasRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == TareasViewModel::class.java) {
            "ViewModel no soportado: ${modelClass.name}"
        }
        @Suppress("UNCHECKED_CAST")
        return TareasViewModel(repo) as T
    }
}
