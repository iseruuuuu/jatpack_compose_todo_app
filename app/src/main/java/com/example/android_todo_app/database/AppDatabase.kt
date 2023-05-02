package com.example.android_todo_app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android_todo_app.utils.DateTimeConverter
import com.example.android_todo_app.model.Todo

//Roomを使用するために必要な記載

@Database(entities = [Todo::class], version = 1, exportSchema = false)
//DateTimeの変換のために追加した。
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}