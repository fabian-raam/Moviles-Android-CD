package com.ramirez.centrotareas.data

interface TareasRepository {
    suspend fun contarPrimos(hasta: Int, onProgreso: (Float) -> Unit = {}): Int
    suspend fun cargarPerfil(): String
    suspend fun cargarPedidos(simularError: Boolean): List<String>
}
