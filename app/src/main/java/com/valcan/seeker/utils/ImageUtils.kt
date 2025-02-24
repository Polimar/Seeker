package com.valcan.seeker.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object ImageUtils {
    fun createTempImageUri(context: Context): Uri {
        val tempFile = File.createTempFile(
            "temp_photo_",
            ".jpg",
            context.cacheDir
        )
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            tempFile
        )
    }

    fun processAndSaveImage(context: Context, uri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        
        // Ridimensiona l'immagine a una risoluzione media (es. 800px di larghezza)
        val scaledBitmap = scaleBitmap(bitmap, 800)
        
        // Salva l'immagine nella directory dell'app
        val imageFile = File(context.filesDir, "photo_${System.currentTimeMillis()}.jpg")
        FileOutputStream(imageFile).use { out ->
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
        }
        
        return imageFile.absolutePath
    }

    private fun scaleBitmap(bitmap: Bitmap, maxWidth: Int): Bitmap {
        val ratio = maxWidth.toFloat() / bitmap.width
        val newWidth = maxWidth
        val newHeight = (bitmap.height * ratio).toInt()
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }
} 