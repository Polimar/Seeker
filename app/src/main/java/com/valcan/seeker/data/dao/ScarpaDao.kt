package com.valcan.seeker.data.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.valcan.seeker.data.db.DatabaseConstants.Scarpe
import com.valcan.seeker.data.model.Scarpa

class ScarpaDao(private val db: SQLiteDatabase) : BaseDao<Scarpa> {
    
    override fun insert(item: Scarpa): Long {
        val values = ContentValues().apply {
            put(Scarpe.COLUMN_DESCRIZIONE, item.descrizione)
            put(Scarpe.COLUMN_TIPO, item.tipo)
            put(Scarpe.COLUMN_COLORE, item.colore)
            put(Scarpe.COLUMN_ARMADIO_ID, item.armadioId)
            put(Scarpe.COLUMN_POSIZIONE, item.posizione)
            put(Scarpe.COLUMN_UTENTE_ID, item.utenteId)
            put(Scarpe.COLUMN_DATA_INSERIMENTO, item.dataInserimento)
            put(Scarpe.COLUMN_DATA_RICERCA, item.dataRicerca)
            put(Scarpe.COLUMN_DATA_SELEZIONE, item.dataSelezione)
            put(Scarpe.COLUMN_FOTO, item.foto)
        }
        return db.insert(Scarpe.TABLE_NAME, null, values)
    }

    override fun update(item: Scarpa): Int {
        val values = ContentValues().apply {
            put(Scarpe.COLUMN_DESCRIZIONE, item.descrizione)
            put(Scarpe.COLUMN_TIPO, item.tipo)
            put(Scarpe.COLUMN_COLORE, item.colore)
            put(Scarpe.COLUMN_ARMADIO_ID, item.armadioId)
            put(Scarpe.COLUMN_POSIZIONE, item.posizione)
            put(Scarpe.COLUMN_UTENTE_ID, item.utenteId)
            put(Scarpe.COLUMN_DATA_INSERIMENTO, item.dataInserimento)
            put(Scarpe.COLUMN_DATA_RICERCA, item.dataRicerca)
            put(Scarpe.COLUMN_DATA_SELEZIONE, item.dataSelezione)
            put(Scarpe.COLUMN_FOTO, item.foto)
        }
        return db.update(
            Scarpe.TABLE_NAME,
            values,
            "${Scarpe.COLUMN_ID} = ?",
            arrayOf(item.id.toString())
        )
    }

    override fun delete(item: Scarpa): Int {
        return db.delete(
            Scarpe.TABLE_NAME,
            "${Scarpe.COLUMN_ID} = ?",
            arrayOf(item.id.toString())
        )
    }

    override fun getById(id: Long): Scarpa? {
        val cursor = db.query(
            Scarpe.TABLE_NAME,
            null,
            "${Scarpe.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return cursor.use { 
            if (it.moveToFirst()) cursorToScarpa(it) else null 
        }
    }

    override fun getAll(): List<Scarpa> {
        val scarpe = mutableListOf<Scarpa>()
        val cursor = db.query(
            Scarpe.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        cursor.use {
            while (it.moveToNext()) {
                scarpe.add(cursorToScarpa(it))
            }
        }
        return scarpe
    }

    private fun cursorToScarpa(cursor: Cursor): Scarpa {
        return Scarpa(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(Scarpe.COLUMN_ID)),
            descrizione = cursor.getString(cursor.getColumnIndexOrThrow(Scarpe.COLUMN_DESCRIZIONE)),
            tipo = cursor.getString(cursor.getColumnIndexOrThrow(Scarpe.COLUMN_TIPO)),
            colore = cursor.getString(cursor.getColumnIndexOrThrow(Scarpe.COLUMN_COLORE)),
            armadioId = cursor.getLong(cursor.getColumnIndexOrThrow(Scarpe.COLUMN_ARMADIO_ID)),
            posizione = cursor.getString(cursor.getColumnIndexOrThrow(Scarpe.COLUMN_POSIZIONE)),
            utenteId = cursor.getLong(cursor.getColumnIndexOrThrow(Scarpe.COLUMN_UTENTE_ID)),
            dataInserimento = cursor.getString(cursor.getColumnIndexOrThrow(Scarpe.COLUMN_DATA_INSERIMENTO)),
            dataRicerca = cursor.getString(cursor.getColumnIndexOrThrow(Scarpe.COLUMN_DATA_RICERCA)),
            dataSelezione = cursor.getString(cursor.getColumnIndexOrThrow(Scarpe.COLUMN_DATA_SELEZIONE)),
            foto = cursor.getString(cursor.getColumnIndexOrThrow(Scarpe.COLUMN_FOTO))
        )
    }

    fun getByArmadioId(armadioId: Long): List<Scarpa> {
        val scarpe = mutableListOf<Scarpa>()
        val cursor = db.query(
            Scarpe.TABLE_NAME,
            null,
            "${Scarpe.COLUMN_ARMADIO_ID} = ?",
            arrayOf(armadioId.toString()),
            null,
            null,
            null
        )
        cursor.use {
            while (it.moveToNext()) {
                scarpe.add(cursorToScarpa(it))
            }
        }
        return scarpe
    }

    fun getByUtenteId(utenteId: Long): List<Scarpa> {
        val scarpe = mutableListOf<Scarpa>()
        val cursor = db.query(
            Scarpe.TABLE_NAME,
            null,
            "${Scarpe.COLUMN_UTENTE_ID} = ?",
            arrayOf(utenteId.toString()),
            null,
            null,
            null
        )
        cursor.use {
            while (it.moveToNext()) {
                scarpe.add(cursorToScarpa(it))
            }
        }
        return scarpe
    }
} 