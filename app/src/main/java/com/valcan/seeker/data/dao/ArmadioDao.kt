package com.valcan.seeker.data.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.valcan.seeker.data.db.DatabaseConstants.Armadi
import com.valcan.seeker.data.db.DatabaseConstants.Vestiti
import com.valcan.seeker.data.db.DatabaseConstants.Scarpe
import com.valcan.seeker.data.model.Armadio

class ArmadioDao(private val db: SQLiteDatabase) : BaseDao<Armadio> {
    
    override fun insert(item: Armadio): Long {
        val values = ContentValues().apply {
            put(Armadi.COLUMN_NOME, item.nome)
            put(Armadi.COLUMN_POSIZIONE, item.posizione)
            put(Armadi.COLUMN_NASCOSTO, if (item.nascosto) 1 else 0)
        }
        return db.insert(Armadi.TABLE_NAME, null, values)
    }

    override fun update(item: Armadio): Int {
        val values = ContentValues().apply {
            put(Armadi.COLUMN_NOME, item.nome)
            put(Armadi.COLUMN_POSIZIONE, item.posizione)
            put(Armadi.COLUMN_NASCOSTO, if (item.nascosto) 1 else 0)
        }
        return db.update(
            Armadi.TABLE_NAME,
            values,
            "${Armadi.COLUMN_ID} = ?",
            arrayOf(item.id.toString())
        )
    }

    override fun delete(item: Armadio): Int {
        return db.delete(
            Armadi.TABLE_NAME,
            "${Armadi.COLUMN_ID} = ?",
            arrayOf(item.id.toString())
        )
    }

    override fun getById(id: Long): Armadio? {
        val cursor = db.query(
            Armadi.TABLE_NAME,
            null,
            "${Armadi.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return cursor.use { 
            if (it.moveToFirst()) cursorToArmadio(it) else null 
        }
    }

    override fun getAll(): List<Armadio> {
        val cursor = db.query(
            Armadi.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        
        val armadi = mutableListOf<Armadio>()
        
        cursor.use {
            while (it.moveToNext()) {
                armadi.add(cursorToArmadio(it))
            }
        }
        
        return armadi
    }

    private fun cursorToArmadio(cursor: Cursor): Armadio {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(Armadi.COLUMN_ID))
        return Armadio(
            id = id,
            nome = cursor.getString(cursor.getColumnIndexOrThrow(Armadi.COLUMN_NOME)),
            posizione = cursor.getString(cursor.getColumnIndexOrThrow(Armadi.COLUMN_POSIZIONE)),
            nascosto = cursor.getInt(cursor.getColumnIndexOrThrow(Armadi.COLUMN_NASCOSTO)) == 1,
            numeroVestiti = getNumeroVestiti(id),
            numeroScarpe = getNumeroScarpe(id)
        )
    }

    private fun getNumeroVestiti(armadioId: Long): Int {
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM ${Vestiti.TABLE_NAME} WHERE ${Vestiti.COLUMN_ARMADIO_ID} = ?",
            arrayOf(armadioId.toString())
        )
        return cursor.use {
            if (it.moveToFirst()) it.getInt(0) else 0
        }
    }

    private fun getNumeroScarpe(armadioId: Long): Int {
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM ${Scarpe.TABLE_NAME} WHERE ${Scarpe.COLUMN_ARMADIO_ID} = ?",
            arrayOf(armadioId.toString())
        )
        return cursor.use {
            if (it.moveToFirst()) it.getInt(0) else 0
        }
    }
} 