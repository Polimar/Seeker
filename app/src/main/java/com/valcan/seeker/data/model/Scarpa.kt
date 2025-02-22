package com.valcan.seeker.data.model

data class Scarpa(
    val id: Long = 0,
    val descrizione: String,
    val tipo: String,
    val colore: String,
    val armadioId: Long,
    val posizione: String,
    val utenteId: Long,
    val dataInserimento: String,
    val dataRicerca: String? = null,
    val dataSelezione: String? = null,
    val foto: String? = null
) 