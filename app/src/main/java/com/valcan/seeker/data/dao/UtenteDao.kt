package com.valcan.seeker.data.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.valcan.seeker.data.db.DatabaseConstants.Utenti
import com.valcan.seeker.data.model.Utente

class UtenteDao(private val db: SQLiteDatabase) : BaseDao<Utente> {
    
    override fun insert(item: Utente): Long {
        val values = ContentValues().apply {
            put(Utenti.COLUMN_NOME, item.nome)
            put(Utenti.COLUMN_DATA_NASCITA, item.dataNascita)
            put(Utenti.COLUMN_SESSO, item.sesso)
            put(Utenti.COLUMN_FOTO, item.foto)
        }
        return db.insert(Utenti.TABLE_NAME, null, values)
    }

    override fun update(item: Utente): Int {
        val values = ContentValues().apply {
            put(Utenti.COLUMN_NOME, item.nome)
            put(Utenti.COLUMN_DATA_NASCITA, item.dataNascita)
            put(Utenti.COLUMN_SESSO, item.sesso)
            put(Utenti.COLUMN_FOTO, item.foto)
        }
        return db.update(
            Utenti.TABLE_NAME,
            values,
            "${Utenti.COLUMN_ID} = ?",
            arrayOf(item.id.toString())
        )
    }

    override fun delete(item: Utente): Int {
        return db.delete(
            Utenti.TABLE_NAME,
            "${Utenti.COLUMN_ID} = ?",
            arrayOf(item.id.toString())
        )
    }

    override fun getById(id: Long): Utente? {
        val cursor = db.query(
            Utenti.TABLE_NAME,
            null,
            "${Utenti.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return cursor.use { 
            if (it.moveToFirst()) cursorToUtente(it) else null 
        }
    }

    override fun getAll(): List<Utente> {
        val utenti = mutableListOf<Utente>()
        val cursor = db.query(
            Utenti.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        cursor.use {
            while (it.moveToNext()) {
                utenti.add(cursorToUtente(it))
            }
        }
        return utenti
    }

    private fun cursorToUtente(cursor: Cursor): Utente {
        return Utente(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(Utenti.COLUMN_ID)),
            nome = cursor.getString(cursor.getColumnIndexOrThrow(Utenti.COLUMN_NOME)),
            dataNascita = cursor.getString(cursor.getColumnIndexOrThrow(Utenti.COLUMN_DATA_NASCITA)),
            sesso = cursor.getString(cursor.getColumnIndexOrThrow(Utenti.COLUMN_SESSO)),
            foto = cursor.getString(cursor.getColumnIndexOrThrow(Utenti.COLUMN_FOTO))
        )
    }
} 