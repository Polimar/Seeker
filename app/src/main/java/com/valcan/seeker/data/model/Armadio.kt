package com.valcan.seeker.data.model

data class Armadio(
    val id: Long = 0,
    val nome: String,
    val posizione: String,
    val nascosto: Boolean = false
) 