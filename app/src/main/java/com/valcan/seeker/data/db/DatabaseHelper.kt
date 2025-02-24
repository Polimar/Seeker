package com.valcan.seeker.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.valcan.seeker.data.db.DatabaseConstants.Armadi
import com.valcan.seeker.data.db.DatabaseConstants.Vestiti
import com.valcan.seeker.data.db.DatabaseConstants.Scarpe
import com.valcan.seeker.data.db.DatabaseConstants.Utenti

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DatabaseConstants.DATABASE_NAME,
    null,
    DatabaseConstants.DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE ${Armadi.TABLE_NAME} (
                ${Armadi.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Armadi.COLUMN_NOME} TEXT NOT NULL,
                ${Armadi.COLUMN_POSIZIONE} TEXT NOT NULL,
                ${Armadi.COLUMN_CREATORE_ID} INTEGER NOT NULL,
                FOREIGN KEY (${Armadi.COLUMN_CREATORE_ID}) 
                REFERENCES ${Utenti.TABLE_NAME}(${Utenti.COLUMN_ID})
            )
        """)

        // Creazione tabella Utenti
        db.execSQL(CREATE_TABLE_UTENTI)

        // Creazione tabella Vestiti
        db.execSQL("""
            CREATE TABLE ${Vestiti.TABLE_NAME} (
                ${Vestiti.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Vestiti.COLUMN_DESCRIZIONE} TEXT NOT NULL,
                ${Vestiti.COLUMN_TIPO} TEXT NOT NULL,
                ${Vestiti.COLUMN_COLORE} TEXT NOT NULL,
                ${Vestiti.COLUMN_ARMADIO_ID} INTEGER NOT NULL,
                ${Vestiti.COLUMN_POSIZIONE} TEXT NOT NULL,
                ${Vestiti.COLUMN_UTENTE_ID} INTEGER NOT NULL,
                ${Vestiti.COLUMN_DATA_INSERIMENTO} TEXT NOT NULL,
                ${Vestiti.COLUMN_DATA_RICERCA} TEXT,
                ${Vestiti.COLUMN_DATA_SELEZIONE} TEXT,
                ${Vestiti.COLUMN_FOTO} TEXT,
                FOREIGN KEY(${Vestiti.COLUMN_ARMADIO_ID}) REFERENCES ${Armadi.TABLE_NAME}(${Armadi.COLUMN_ID}),
                FOREIGN KEY(${Vestiti.COLUMN_UTENTE_ID}) REFERENCES ${Utenti.TABLE_NAME}(${Utenti.COLUMN_ID})
            )
        """.trimIndent())

        // Creazione tabella Scarpe
        db.execSQL("""
            CREATE TABLE ${Scarpe.TABLE_NAME} (
                ${Scarpe.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Scarpe.COLUMN_DESCRIZIONE} TEXT NOT NULL,
                ${Scarpe.COLUMN_TIPO} TEXT NOT NULL,
                ${Scarpe.COLUMN_COLORE} TEXT NOT NULL,
                ${Scarpe.COLUMN_ARMADIO_ID} INTEGER NOT NULL,
                ${Scarpe.COLUMN_POSIZIONE} TEXT NOT NULL,
                ${Scarpe.COLUMN_UTENTE_ID} INTEGER NOT NULL,
                ${Scarpe.COLUMN_DATA_INSERIMENTO} TEXT NOT NULL,
                ${Scarpe.COLUMN_DATA_RICERCA} TEXT,
                ${Scarpe.COLUMN_DATA_SELEZIONE} TEXT,
                ${Scarpe.COLUMN_FOTO} TEXT,
                FOREIGN KEY(${Scarpe.COLUMN_ARMADIO_ID}) REFERENCES ${Armadi.TABLE_NAME}(${Armadi.COLUMN_ID}),
                FOREIGN KEY(${Scarpe.COLUMN_UTENTE_ID}) REFERENCES ${Utenti.TABLE_NAME}(${Utenti.COLUMN_ID})
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            // Aggiungi la colonna sesso alla tabella utenti
            db.execSQL("ALTER TABLE ${Utenti.TABLE_NAME} ADD COLUMN ${Utenti.COLUMN_SESSO} TEXT DEFAULT 'M' NOT NULL")
        }
    }

    private val CREATE_TABLE_UTENTI = """
        CREATE TABLE ${Utenti.TABLE_NAME} (
            ${Utenti.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Utenti.COLUMN_NOME} TEXT NOT NULL,
            ${Utenti.COLUMN_DATA_NASCITA} TEXT NOT NULL,
            ${Utenti.COLUMN_SESSO} TEXT NOT NULL,
            ${Utenti.COLUMN_FOTO} TEXT
        )
    """

    companion object {
        private const val CREATE_TABLE_ARMADIO = """
            CREATE TABLE ${Armadi.TABLE_NAME} (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                descrizione TEXT,
                creatore_id INTEGER,
                FOREIGN KEY (creatore_id) REFERENCES ${Utenti.TABLE_NAME}(${Utenti.COLUMN_ID})
            )
        """
    }
} 