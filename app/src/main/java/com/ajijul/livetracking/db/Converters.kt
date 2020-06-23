package com.ajijul.livetracking.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.io.OutputStream

class Converters {

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        return byteArray?.let { BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size) }
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toFilterType(string: String): FilterType {
        return FilterType.valueOf(string)
    }

    @TypeConverter
    fun fromFilterType(filterType: FilterType): String {
        return filterType.type
    }
}