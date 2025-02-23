package com.valcan.seeker.data.model

data class Utente(
    val id: Long = 0,
    val nome: String,
    val dataNascita: String,
    val sesso: String,  // "M" o "F"
    val foto: String? = null
) 