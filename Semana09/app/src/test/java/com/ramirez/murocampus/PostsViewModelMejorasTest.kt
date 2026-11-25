package com.ramirez.murocampus

import com.ramirez.murocampus.data.PostRepository
import com.ramirez.murocampus.model.Comentario
import com.ramirez.murocampus.model.NuevoPost
import com.ramirez.murocampus.model.Post
import com.ramirez.murocampus.viewmodel.PostsViewModel
import com.ramirez.murocampus.viewmodel.UiState
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostsViewModelMejorasTest {
    private val dispatcher = StandardTestDispatcher()

    @Before fun configurar() { Dispatchers.setMain(dispatcher) }
    @After fun terminar() { Dispatchers.resetMain() }

    @Test fun cargaVeinteYAgregaLaSiguientePaginaSinDuplicados() = runTest {
        val repo = RepoDePrueba()
        val vm = PostsViewModel(repo)
        advanceUntilIdle()
        assertEquals(20, visibles(vm).size)
        vm.cargarMas()
        advanceUntilIdle()
        assertEquals((1..40).toList(), visibles(vm).map { it.id })
        assertEquals(listOf(Peticion(0, 20, null), Peticion(20, 20, null)), repo.peticiones)
    }

    @Test fun buscaSinRedYReaplicaLaBusquedaAlAgregarPaginas() = runTest {
        val repo = RepoDePrueba()
        val vm = PostsViewModel(repo)
        advanceUntilIdle()
        vm.buscar("  tItUlO 35  ")
        assertTrue(visibles(vm).isEmpty())
        assertEquals(1, repo.peticiones.size)
        vm.cargarMas()
        advanceUntilIdle()
        assertEquals(listOf(35), visibles(vm).map { it.id })
        assertEquals(40, vm.cantidadDescargada.value)
        vm.buscar("")
        assertEquals(40, visibles(vm).size)
        assertEquals(2, repo.peticiones.size)
    }

    @Test fun falloAlCargarMasConservaDatosYReintentaElMismoInicio() = runTest {
        val repo = RepoDePrueba()
        var fallar = true
        repo.responder = { peticion ->
            if (peticion.inicio == 20 && fallar) {
                fallar = false
                Result.failure(IOException("Sin conexión"))
            } else Result.success(repo.datos(peticion))
        }
        val vm = PostsViewModel(repo)
        advanceUntilIdle()
        vm.cargarMas()
        advanceUntilIdle()
        assertEquals(20, visibles(vm).size)
        assertTrue(vm.cargaMas.value is UiState.Error)
        vm.cargarMas()
        advanceUntilIdle()
        assertEquals(40, visibles(vm).size)
        assertEquals(listOf(0, 20, 20), repo.peticiones.map { it.inicio })
    }

    @Test fun cambiarUsuarioReiniciaLaPaginacionYConservaLaConsulta() = runTest {
        val repo = RepoDePrueba()
        val vm = PostsViewModel(repo)
        advanceUntilIdle()
        vm.cargarMas()
        advanceUntilIdle()
        vm.buscar("Titulo 1")
        vm.filtrar(2)
        advanceUntilIdle()
        assertEquals(Peticion(0, 20, 2), repo.peticiones.last())
        assertEquals(10, vm.cantidadDescargada.value)
        assertEquals(2, vm.usuarioFiltro.value)
        assertEquals("Titulo 1", vm.busqueda.value)
        assertTrue(visibles(vm).all { it.userId == 2 })
        assertFalse(vm.hayMas.value)
    }

    @Test fun respuestaViejaNoReemplazaElFiltroNuevo() = runTest {
        val repo = RepoDePrueba()
        repo.responder = { peticion ->
            if (peticion.userId == null) delay(1000)
            Result.success(repo.datos(peticion))
        }
        val vm = PostsViewModel(repo)
        runCurrent()
        vm.filtrar(2)
        advanceUntilIdle()
        assertEquals(10, visibles(vm).size)
        assertTrue(visibles(vm).all { it.userId == 2 })
    }

    @Test fun dejaDePedirPaginasAlAgotarseLosResultados() = runTest {
        val repo = RepoDePrueba()
        val vm = PostsViewModel(repo)
        advanceUntilIdle()
        repeat(5) { vm.cargarMas(); advanceUntilIdle() }
        assertEquals(100, visibles(vm).size)
        assertFalse(vm.hayMas.value)
        vm.cargarMas()
        advanceUntilIdle()
        assertEquals(listOf(0, 20, 40, 60, 80, 100), repo.peticiones.map { it.inicio })
    }

    @Test fun eliminarNoDesplazaElCursorNiReapareceAlLimpiarBusqueda() = runTest {
        val repo = RepoDePrueba()
        val vm = PostsViewModel(repo)
        advanceUntilIdle()
        vm.abrir(1)
        advanceUntilIdle()
        vm.eliminar(1)
        advanceUntilIdle()
        assertTrue(vm.eliminacion.value is UiState.Exito)
        assertEquals(19, visibles(vm).size)
        vm.buscar("Titulo 1")
        assertFalse(visibles(vm).any { it.id == 1 })
        vm.buscar("")
        vm.cargarMas()
        advanceUntilIdle()
        assertEquals(20, repo.peticiones.last().inicio)
        assertEquals(39, visibles(vm).size)
        assertFalse(visibles(vm).any { it.id == 1 })
    }

    @Test fun doblePulsacionNoDuplicaLaPeticionDePagina() = runTest {
        val repo = RepoDePrueba()
        repo.responder = { peticion ->
            if (peticion.inicio == 20) delay(1000)
            Result.success(repo.datos(peticion))
        }
        val vm = PostsViewModel(repo)
        advanceUntilIdle()
        vm.cargarMas()
        vm.cargarMas()
        advanceUntilIdle()
        assertEquals(2, repo.peticiones.size)
        assertEquals(40, visibles(vm).size)
    }

    private fun visibles(vm: PostsViewModel): List<Post> =
        (vm.lista.value as UiState.Exito).datos

    private data class Peticion(val inicio: Int, val limite: Int, val userId: Int?)

    private class RepoDePrueba : PostRepository {
        private val posts = (1..100).map { Post((it - 1) / 10 + 1, it, "Titulo $it", "Texto $it") }
        val peticiones = mutableListOf<Peticion>()
        var responder: suspend (Peticion) -> Result<List<Post>> = { Result.success(datos(it)) }

        fun datos(peticion: Peticion): List<Post> = posts
            .filter { peticion.userId == null || it.userId == peticion.userId }
            .drop(peticion.inicio).take(peticion.limite)

        override suspend fun pagina(inicio: Int, limite: Int, userId: Int?): Result<List<Post>> {
            val peticion = Peticion(inicio, limite, userId)
            peticiones.add(peticion)
            return responder(peticion)
        }
        override suspend fun listar(): Result<List<Post>> = error("La lista debe usar paginación")
        override suspend fun porUsuario(userId: Int): Result<List<Post>> = error("El filtro debe usar paginación")
        override suspend fun detalle(id: Int): Result<Post> = Result.success(posts.first { it.id == id })
        override suspend fun comentarios(id: Int): Result<List<Comentario>> = Result.success(emptyList())
        override suspend fun crear(nuevo: NuevoPost): Result<Post> = error("No usado")
        override suspend fun eliminar(id: Int): Result<Unit> = Result.success(Unit)
    }
}
