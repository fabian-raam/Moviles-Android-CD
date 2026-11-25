package com.ramirez.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.murocampus.data.UsuarioRepository
import com.ramirez.murocampus.data.mensajeDe
import com.ramirez.murocampus.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuariosViewModel(private val repo: UsuarioRepository) : ViewModel() {
    private val _usuarios = MutableStateFlow<UiState<List<Usuario>>>(UiState.Cargando)
    val usuarios = _usuarios.asStateFlow()

    init { cargar() }

    fun cargar() {
        viewModelScope.launch {
            _usuarios.value = UiState.Cargando
            repo.listar()
                .onSuccess { _usuarios.value = UiState.Exito(it) }
                .onFailure { _usuarios.value = UiState.Error(mensajeDe(it)) }
        }
    }
}
