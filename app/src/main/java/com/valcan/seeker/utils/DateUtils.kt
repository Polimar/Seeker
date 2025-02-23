package com.valcan.seeker.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    fun getCurrentDateTime(): String {
        return dateFormat.format(Date())
    }
} 