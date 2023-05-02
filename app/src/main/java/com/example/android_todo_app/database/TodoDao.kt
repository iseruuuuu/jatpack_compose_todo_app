package com.example.android_todo_app.database

import androidx.room.*
import com.example.android_todo_app.model.Todo

//データアクセスオブジェクト（DAO）
//データベースとのやり取りを効率よく・安全に行える

//データベース操作の抽象化が可能になります
//実行時のエラーやデータベース操作の問題を事前に検出し、修正することができる
//データベース操作に関連するコードを一箇所にまとめることができる
//データベース操作を簡潔なメソッドやアノテーションで定義できます
//バックグラウンドで非同期的にデータベース操作を行うことができる


@Dao
interface TodoDao {
    @Query("select * from todos order by created_at asc")
    fun getAll(): MutableList<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun post(todo: Todo)

    @Delete
    fun delete(todo: Todo)
}