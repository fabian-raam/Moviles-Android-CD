package com.ramirez.murocampus.data

import com.ramirez.murocampus.data.remote.UsuarioApi
import com.ramirez.murocampus.model.Usuario
import kotlin.coroutines.cancellation.CancellationException

interface UsuarioRepository {
    suspend fun listar(): Result<List<Usuario>>
}

class UsuarioRepositoryRemote(private val api: UsuarioApi) : UsuarioRepository {
    override suspend fun listar(): Result<List<Usuario>> = try {
        Result.success(api.listar())
    } catch (error: CancellationException) {
        throw error
    } catch (error: Exception) {
        Result.failure(error)
    }
}
