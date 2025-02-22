package com.valcan.seeker.data.dao

interface BaseDao<T> {
    fun insert(item: T): Long
    fun update(item: T): Int
    fun delete(item: T): Int
    fun getById(id: Long): T?
    fun getAll(): List<T>
} 