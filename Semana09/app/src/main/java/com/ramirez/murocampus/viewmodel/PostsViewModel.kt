package com.ramirez.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.murocampus.data.PostRepository
import com.ramirez.murocampus.data.mensajeDe
import com.ramirez.murocampus.model.Post
import com.ramirez.murocampus.model.Comentario
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetalleUi(val post: Post, val comentarios: List<Comentario>)

class PostsViewModel(private val repo: PostRepository) : ViewModel() {
    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista = _lista.asStateFlow()
    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle = _detalle.asStateFlow()
    private var detalleId: Int? = null
    private var detalleJob: Job? = null

    init { cargar() }

    fun cargar() {
        viewModelScope.launch {
            _lista.value = UiState.Cargando
            repo.listar()
                .onSuccess { _lista.value = UiState.Exito(it) }
                .onFailure { _lista.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun cargarDetalleSiNecesario(id: Int) {
        if (detalleId != id) abrir(id)
    }

    fun abrir(id: Int) {
        detalleJob?.cancel()
        detalleId = id
        _detalle.value = UiState.Cargando
        detalleJob = viewModelScope.launch {
            repo.detalle(id)
                .onSuccess { post ->
                    repo.comentarios(id)
                        .onSuccess { comentarios ->
                            _detalle.value = UiState.Exito(DetalleUi(post, comentarios))
                        }
                        .onFailure { _detalle.value = UiState.Error(mensajeDe(it)) }
                }
                .onFailure { _detalle.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun cerrarDetalle() {
        detalleJob?.cancel()
        detalleJob = null
        detalleId = null
        _detalle.value = UiState.Cargando
    }
}
