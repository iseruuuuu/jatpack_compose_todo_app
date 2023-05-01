package com.example.android_todo_app

import androidx.room.TypeConverter
import java.util.*

//DateTimeConverterクラスを作成する
//RoomではDate型を扱えないため、Long型にキャストするコンバーターを作成する。

class DateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}