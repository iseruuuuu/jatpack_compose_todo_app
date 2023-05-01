package com.example.android_todo_app

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android_todo_app.Todo

//todo_daoのimport
// RoomDatabaseを実装する


@Database(entities = [Todo::class], version = 1, exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}