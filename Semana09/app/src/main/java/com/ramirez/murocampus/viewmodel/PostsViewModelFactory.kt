package com.ramirez.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// [E] Inyecta el Repository desde MainActivity mediante una función de creación.
// Uso futuro: PostsViewModelFactory(PostsViewModel::class.java) { PostsViewModel(repo) }
class PostsViewModelFactory<T : ViewModel>(
    private val tipo: Class<T>,
    private val crear: () -> T
) : ViewModelProvider.Factory {
    override fun <M : ViewModel> create(modelClass: Class<M>): M {
        require(modelClass.isAssignableFrom(tipo)) {
            "ViewModel no soportado: ${modelClass.name}"
        }
        return modelClass.cast(crear())
            ?: error("No se pudo crear el ViewModel")
    }
}
