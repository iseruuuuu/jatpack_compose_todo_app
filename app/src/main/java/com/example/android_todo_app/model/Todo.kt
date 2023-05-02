package com.example.android_todo_app.model

import androidx.room.*
import java.util.*

//Roomを使用するときに設定する
@Entity(tableName = "todos")
data class Todo(
    //新しいTodoオブジェクトが挿入されるたびにIDが生成される
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val created_at: Date = Date()
)