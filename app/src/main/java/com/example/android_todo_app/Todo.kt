package com.example.android_todo_app

import androidx.room.*
import java.util.*


@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val created_at: Date = Date()
)