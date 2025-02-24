package com.valcan.seeker.data.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.valcan.seeker.data.model.Armadio
import com.valcan.seeker.data.db.DatabaseConstants.Armadi
import com.valcan.seeker.data.db.DatabaseConstants.Vestiti

class ArmadioDao(private val db: SQLiteDatabase) {
    
    fun getArmadiWithVestitiByUtenteId(utenteId: Long): List<Armadio> {
        val query = """
            SELECT DISTINCT a.* 
            FROM ${Armadi.TABLE_NAME} a
            LEFT JOIN ${Vestiti.TABLE_NAME} v ON a.${Armadi.COLUMN_ID} = v.${Vestiti.COLUMN_ARMADIO_ID}
            WHERE a.${Armadi.COLUMN_CREATORE_ID} = ? 
            OR v.${Vestiti.COLUMN_UTENTE_ID} = ?
        """
        
        val cursor = db.rawQuery(query, arrayOf(utenteId.toString(), utenteId.toString()))
        
        val armadi = mutableListOf<Armadio>()
        
        cursor.use {
            while (it.moveToNext()) {
                armadi.add(
                    Armadio(
                        id = it.getLong(it.getColumnIndexOrThrow(Armadi.COLUMN_ID)),
                        nome = it.getString(it.getColumnIndexOrThrow(Armadi.COLUMN_NOME)),
                        posizione = it.getString(it.getColumnIndexOrThrow(Armadi.COLUMN_POSIZIONE)),
                        creatoreId = it.getLong(it.getColumnIndexOrThrow(Armadi.COLUMN_CREATORE_ID))
                    )
                )
            }
        }
        
        return armadi
    }

    fun insert(armadio: Armadio): Long {
        val values = ContentValues().apply {
            put(Armadi.COLUMN_NOME, armadio.nome)
            put(Armadi.COLUMN_POSIZIONE, armadio.posizione)
            put(Armadi.COLUMN_CREATORE_ID, armadio.creatoreId)
        }
        
        return db.insert(Armadi.TABLE_NAME, null, values)
    }

    // ... altri metodi esistenti ...
}