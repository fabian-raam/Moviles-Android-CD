package com.ramirez.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramirez.murocampus.data.PostRepository
import com.ramirez.murocampus.data.mensajeDe
import com.ramirez.murocampus.model.Post
import com.ramirez.murocampus.model.Comentario
import com.ramirez.murocampus.model.NuevoPost
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetalleUi(val post: Post, val comentarios: List<Comentario>)

class PostsViewModel(private val repo: PostRepository) : ViewModel() {
    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista = _lista.asStateFlow()
    private val _usuarioFiltro = MutableStateFlow<Int?>(null)
    val usuarioFiltro = _usuarioFiltro.asStateFlow()
    private var listaJob: Job? = null
    private var postsDescargados = emptyList<Post>()
    private val _busqueda = MutableStateFlow("")
    val busqueda = _busqueda.asStateFlow()
    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle = _detalle.asStateFlow()
    private var detalleId: Int? = null
    private var detalleJob: Job? = null
    private val _publicacion = MutableStateFlow<UiState<Post>?>(null)
    val publicacion = _publicacion.asStateFlow()
    private var publicacionJob: Job? = null
    private val _eliminacion = MutableStateFlow<UiState<Unit>?>(null)
    val eliminacion = _eliminacion.asStateFlow()
    private var eliminacionJob: Job? = null

    init { cargar() }

    fun cargar() {
        listaJob?.cancel()
        val userId = _usuarioFiltro.value
        _lista.value = UiState.Cargando
        listaJob = viewModelScope.launch {
            val resultado = if (userId == null) repo.listar() else repo.porUsuario(userId)
            resultado
                .onSuccess {
                    postsDescargados = it
                    actualizarListaVisible()
                }
                .onFailure { _lista.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun buscar(texto: String) {
        _busqueda.value = texto
        if (_lista.value is UiState.Exito) actualizarListaVisible()
    }

    private fun actualizarListaVisible() {
        val consulta = _busqueda.value.trim()
        _lista.value = UiState.Exito(
            postsDescargados.filter { it.title.contains(consulta, ignoreCase = true) }
        )
    }

    fun filtrar(userId: Int?) {
        _usuarioFiltro.value = userId
        cargar()
    }

    fun cargarDetalleSiNecesario(id: Int) {
        if (detalleId != id) abrir(id)
    }

    fun abrir(id: Int) {
        detalleJob?.cancel()
        reiniciarEliminacion()
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
        reiniciarEliminacion()
        detalleJob = null
        detalleId = null
        _detalle.value = UiState.Cargando
    }

    fun eliminar(id: Int) {
        val actual = _detalle.value as? UiState.Exito ?: return
        if (actual.datos.post.id != id || eliminacionJob?.isActive == true ||
            _eliminacion.value is UiState.Exito) return
        _eliminacion.value = UiState.Cargando
        eliminacionJob = viewModelScope.launch {
            repo.eliminar(id)
                .onSuccess {
                    postsDescargados = postsDescargados.filterNot { it.id == id }
                    if (_lista.value is UiState.Exito) actualizarListaVisible()
                    _eliminacion.value = UiState.Exito(Unit)
                }
                .onFailure { _eliminacion.value = UiState.Error(mensajeDe(it)) }
        }
    }

    private fun reiniciarEliminacion() {
        eliminacionJob?.cancel()
        eliminacionJob = null
        _eliminacion.value = null
    }

    fun publicar(titulo: String, texto: String) {
        if (titulo.isBlank() || texto.isBlank() || publicacionJob?.isActive == true) return
        val nuevo = NuevoPost(userId = 1, title = titulo, body = texto)
        _publicacion.value = UiState.Cargando
        publicacionJob = viewModelScope.launch {
            repo.crear(nuevo)
                .onSuccess { _publicacion.value = UiState.Exito(it) }
                .onFailure { _publicacion.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun limpiarResultadoPublicacion() {
        if (_publicacion.value != UiState.Cargando) _publicacion.value = null
    }

    fun reiniciarPublicacion() {
        publicacionJob?.cancel()
        publicacionJob = null
        _publicacion.value = null
    }
}
