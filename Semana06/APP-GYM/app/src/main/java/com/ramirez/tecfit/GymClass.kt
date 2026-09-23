package com.ramirez.tecfit

data class GymClass(
    val name: String,
    val schedule: String,
    val room: String,
    val duration: String = "",
    val availableSlots: Int = 0,
    val totalSlots: Int = 0,
    val description: String = ""
)
