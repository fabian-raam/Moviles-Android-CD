package com.ramirez.murocampus.data

import com.ramirez.murocampus.data.remote.PostApi
import com.ramirez.murocampus.model.Comentario
import com.ramirez.murocampus.model.NuevoPost
import com.ramirez.murocampus.model.Post
import kotlin.coroutines.cancellation.CancellationException
import retrofit2.HttpException

interface PostRepository {
    suspend fun listar(): Result<List<Post>>
    suspend fun detalle(id: Int): Result<Post>
    suspend fun comentarios(id: Int): Result<List<Comentario>>
    suspend fun porUsuario(userId: Int): Result<List<Post>>
    suspend fun crear(nuevo: NuevoPost): Result<Post>
    suspend fun eliminar(id: Int): Result<Unit>
}

class PostRepositoryRemote(private val api: PostApi) : PostRepository {
    override suspend fun listar(): Result<List<Post>> = llamar { api.listar() }

    override suspend fun detalle(id: Int): Result<Post> = llamar { api.detalle(id) }

    override suspend fun comentarios(id: Int): Result<List<Comentario>> =
        llamar { api.comentarios(id) }

    override suspend fun porUsuario(userId: Int): Result<List<Post>> =
        llamar { api.porUsuario(userId) }

    override suspend fun crear(nuevo: NuevoPost): Result<Post> = llamar { api.crear(nuevo) }

    override suspend fun eliminar(id: Int): Result<Unit> = llamar {
        val respuesta = api.eliminar(id)
        if (!respuesta.isSuccessful) throw HttpException(respuesta)
        Unit
    }

    private suspend fun <T> llamar(bloque: suspend () -> T): Result<T> = try {
        Result.success(bloque())
    } catch (error: CancellationException) {
        throw error
    } catch (error: Exception) {
        Result.failure(error)
    }
}
