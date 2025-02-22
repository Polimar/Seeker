package com.valcan.seeker.data.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.valcan.seeker.data.db.DatabaseConstants.Vestiti
import com.valcan.seeker.data.model.Vestito

class VestitoDao(private val db: SQLiteDatabase) : BaseDao<Vestito> {
    
    override fun insert(item: Vestito): Long {
        val values = ContentValues().apply {
            put(Vestiti.COLUMN_DESCRIZIONE, item.descrizione)
            put(Vestiti.COLUMN_TIPO, item.tipo)
            put(Vestiti.COLUMN_COLORE, item.colore)
            put(Vestiti.COLUMN_ARMADIO_ID, item.armadioId)
            put(Vestiti.COLUMN_POSIZIONE, item.posizione)
            put(Vestiti.COLUMN_UTENTE_ID, item.utenteId)
            put(Vestiti.COLUMN_DATA_INSERIMENTO, item.dataInserimento)
            put(Vestiti.COLUMN_DATA_RICERCA, item.dataRicerca)
            put(Vestiti.COLUMN_DATA_SELEZIONE, item.dataSelezione)
            put(Vestiti.COLUMN_FOTO, item.foto)
        }
        return db.insert(Vestiti.TABLE_NAME, null, values)
    }

    override fun update(item: Vestito): Int {
        val values = ContentValues().apply {
            put(Vestiti.COLUMN_DESCRIZIONE, item.descrizione)
            put(Vestiti.COLUMN_TIPO, item.tipo)
            put(Vestiti.COLUMN_COLORE, item.colore)
            put(Vestiti.COLUMN_ARMADIO_ID, item.armadioId)
            put(Vestiti.COLUMN_POSIZIONE, item.posizione)
            put(Vestiti.COLUMN_UTENTE_ID, item.utenteId)
            put(Vestiti.COLUMN_DATA_INSERIMENTO, item.dataInserimento)
            put(Vestiti.COLUMN_DATA_RICERCA, item.dataRicerca)
            put(Vestiti.COLUMN_DATA_SELEZIONE, item.dataSelezione)
            put(Vestiti.COLUMN_FOTO, item.foto)
        }
        return db.update(
            Vestiti.TABLE_NAME,
            values,
            "${Vestiti.COLUMN_ID} = ?",
            arrayOf(item.id.toString())
        )
    }

    override fun delete(item: Vestito): Int {
        return db.delete(
            Vestiti.TABLE_NAME,
            "${Vestiti.COLUMN_ID} = ?",
            arrayOf(item.id.toString())
        )
    }

    override fun getById(id: Long): Vestito? {
        val cursor = db.query(
            Vestiti.TABLE_NAME,
            null,
            "${Vestiti.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return cursor.use { 
            if (it.moveToFirst()) cursorToVestito(it) else null 
        }
    }

    override fun getAll(): List<Vestito> {
        val vestiti = mutableListOf<Vestito>()
        val cursor = db.query(
            Vestiti.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        cursor.use {
            while (it.moveToNext()) {
                vestiti.add(cursorToVestito(it))
            }
        }
        return vestiti
    }

    private fun cursorToVestito(cursor: Cursor): Vestito {
        return Vestito(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(Vestiti.COLUMN_ID)),
            descrizione = cursor.getString(cursor.getColumnIndexOrThrow(Vestiti.COLUMN_DESCRIZIONE)),
            tipo = cursor.getString(cursor.getColumnIndexOrThrow(Vestiti.COLUMN_TIPO)),
            colore = cursor.getString(cursor.getColumnIndexOrThrow(Vestiti.COLUMN_COLORE)),
            armadioId = cursor.getLong(cursor.getColumnIndexOrThrow(Vestiti.COLUMN_ARMADIO_ID)),
            posizione = cursor.getString(cursor.getColumnIndexOrThrow(Vestiti.COLUMN_POSIZIONE)),
            utenteId = cursor.getLong(cursor.getColumnIndexOrThrow(Vestiti.COLUMN_UTENTE_ID)),
            dataInserimento = cursor.getString(cursor.getColumnIndexOrThrow(Vestiti.COLUMN_DATA_INSERIMENTO)),
            dataRicerca = cursor.getString(cursor.getColumnIndexOrThrow(Vestiti.COLUMN_DATA_RICERCA)),
            dataSelezione = cursor.getString(cursor.getColumnIndexOrThrow(Vestiti.COLUMN_DATA_SELEZIONE)),
            foto = cursor.getString(cursor.getColumnIndexOrThrow(Vestiti.COLUMN_FOTO))
        )
    }

    fun getByArmadioId(armadioId: Long): List<Vestito> {
        val vestiti = mutableListOf<Vestito>()
        val cursor = db.query(
            Vestiti.TABLE_NAME,
            null,
            "${Vestiti.COLUMN_ARMADIO_ID} = ?",
            arrayOf(armadioId.toString()),
            null,
            null,
            null
        )
        cursor.use {
            while (it.moveToNext()) {
                vestiti.add(cursorToVestito(it))
            }
        }
        return vestiti
    }

    fun getByUtenteId(utenteId: Long): List<Vestito> {
        val vestiti = mutableListOf<Vestito>()
        val cursor = db.query(
            Vestiti.TABLE_NAME,
            null,
            "${Vestiti.COLUMN_UTENTE_ID} = ?",
            arrayOf(utenteId.toString()),
            null,
            null,
            null
        )
        cursor.use {
            while (it.moveToNext()) {
                vestiti.add(cursorToVestito(it))
            }
        }
        return vestiti
    }
} 