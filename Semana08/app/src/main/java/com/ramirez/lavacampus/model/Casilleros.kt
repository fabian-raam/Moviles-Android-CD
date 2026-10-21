package com.ramirez.lavacampus.model

object Casilleros {
    val todos: List<String> = (1..30).map { i -> "C-%02d".format(i) }
}