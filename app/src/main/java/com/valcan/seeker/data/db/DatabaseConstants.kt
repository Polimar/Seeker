package com.valcan.seeker.data.db

object DatabaseConstants {
    const val DATABASE_NAME = "seeker.db"
    const val DATABASE_VERSION = 3

    // Tabella Armadi
    object Armadi {
        const val TABLE_NAME = "armadi"
        const val COLUMN_ID = "id"
        const val COLUMN_NOME = "nome"
        const val COLUMN_POSIZIONE = "posizione"
        const val COLUMN_NASCOSTO = "nascosto"
    }

    // Tabella Vestiti
    object Vestiti {
        const val TABLE_NAME = "vestiti"
        const val COLUMN_ID = "id"
        const val COLUMN_DESCRIZIONE = "descrizione"
        const val COLUMN_TIPO = "tipo"
        const val COLUMN_COLORE = "colore"
        const val COLUMN_ARMADIO_ID = "armadio_id"
        const val COLUMN_POSIZIONE = "posizione"
        const val COLUMN_UTENTE_ID = "utente_id"
        const val COLUMN_DATA_INSERIMENTO = "data_inserimento"
        const val COLUMN_DATA_RICERCA = "data_ricerca"
        const val COLUMN_DATA_SELEZIONE = "data_selezione"
        const val COLUMN_FOTO = "foto"
    }

    // Tabella Scarpe
    object Scarpe {
        const val TABLE_NAME = "scarpe"
        const val COLUMN_ID = "id"
        const val COLUMN_DESCRIZIONE = "descrizione"
        const val COLUMN_TIPO = "tipo"
        const val COLUMN_COLORE = "colore"
        const val COLUMN_ARMADIO_ID = "armadio_id"
        const val COLUMN_POSIZIONE = "posizione"
        const val COLUMN_UTENTE_ID = "utente_id"
        const val COLUMN_DATA_INSERIMENTO = "data_inserimento"
        const val COLUMN_DATA_RICERCA = "data_ricerca"
        const val COLUMN_DATA_SELEZIONE = "data_selezione"
        const val COLUMN_FOTO = "foto"
    }

    // Tabella Utenti
    object Utenti {
        const val TABLE_NAME = "utenti"
        const val COLUMN_ID = "id"
        const val COLUMN_NOME = "nome"
        const val COLUMN_DATA_NASCITA = "data_nascita"
        const val COLUMN_SESSO = "sesso"
        const val COLUMN_FOTO = "foto"
    }
} 