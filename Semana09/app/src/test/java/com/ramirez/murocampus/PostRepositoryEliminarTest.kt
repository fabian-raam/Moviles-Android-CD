package com.ramirez.murocampus

import com.ramirez.murocampus.data.PostRepositoryRemote
import com.ramirez.murocampus.data.remote.PostApi
import com.ramirez.murocampus.model.Comentario
import com.ramirez.murocampus.model.NuevoPost
import com.ramirez.murocampus.model.Post
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

class PostRepositoryEliminarTest {
    @Test fun aceptaRespuestaExitosaSinCuerpo() = runBlocking {
        var idEnviado: Int? = null
        val repo = PostRepositoryRemote(ApiEliminar { id ->
            idEnviado = id
            Response.success<Unit>(200, null)
        })
        assertEquals(Unit, repo.eliminar(7).getOrThrow())
        assertEquals(7, idEnviado)
    }

    @Test fun respuestaHttpFallidaNoSeTrataComoEliminacionExitosa() = runBlocking {
        val repo = PostRepositoryRemote(ApiEliminar {
            Response.error<Unit>(404, "{}".toResponseBody())
        })
        val error = repo.eliminar(9999).exceptionOrNull()
        assertTrue(error is HttpException)
        assertEquals(404, (error as HttpException).code())
    }

    @Test fun noConvierteCancelacionEnFalloDeRed() = runBlocking {
        val cancelacion = CancellationException("Salir del detalle")
        val repo = PostRepositoryRemote(ApiEliminar { throw cancelacion })
        try {
            repo.eliminar(1)
            fail("La cancelación debe propagarse")
        } catch (error: CancellationException) {
            assertSame(cancelacion, error)
        }
    }

    private class ApiEliminar(private val respuesta: suspend (Int) -> Response<Unit>) : PostApi {
        override suspend fun eliminar(id: Int): Response<Unit> = respuesta(id)
        override suspend fun listar(): List<Post> = error("Llamada no esperada")
        override suspend fun pagina(inicio: Int, limite: Int, userId: Int?): List<Post> =
            error("Llamada no esperada")
        override suspend fun detalle(id: Int): Post = error("Llamada no esperada")
        override suspend fun comentarios(id: Int): List<Comentario> = error("Llamada no esperada")
        override suspend fun porUsuario(userId: Int): List<Post> = error("Llamada no esperada")
        override suspend fun crear(nuevo: NuevoPost): Post = error("Llamada no esperada")
    }
}
