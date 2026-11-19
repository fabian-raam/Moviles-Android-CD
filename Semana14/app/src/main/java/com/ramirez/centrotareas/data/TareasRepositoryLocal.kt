package com.ramirez.centrotareas.data

import java.io.IOException
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext

class TareasRepositoryLocal : TareasRepository {
    override suspend fun cargarPerfil(): String {
        delay(2000)
        return "Ana Torres (ficticia)"
    }

    override suspend fun cargarPedidos(simularError: Boolean): List<String> {
        delay(3000)
        if (simularError) throw IOException("Sin conexión")
        return listOf("Pedido 101", "Pedido 102", "Pedido 103")
    }

    override suspend fun contarPrimos(
        hasta: Int,
        onProgreso: (Float) -> Unit
    ): Int = withContext(Dispatchers.Default) {
        require(hasta >= 2) { "El límite debe ser al menos 2" }
        var cantidad = 0
        var ultimoPorcentaje = 0
        val total = hasta.toLong() - 1
        onProgreso(0f)
        for (n in 2..hasta) {
            ensureActive()
            if (esPrimo(n)) cantidad++
            val porcentaje = ((n.toLong() - 1) * 100 / total).toInt()
            // Informar solo cambios de porcentaje evita saturar la interfaz.
            if (porcentaje > ultimoPorcentaje) {
                onProgreso(porcentaje / 100f)
                ultimoPorcentaje = porcentaje
            }
        }
        cantidad
    }

    private fun esPrimo(n: Int): Boolean {
        if (n < 2) return false
        var d = 2
        while (d.toLong() * d <= n) {
            if (n % d == 0) return false
            d++
        }
        return true
    }
}
