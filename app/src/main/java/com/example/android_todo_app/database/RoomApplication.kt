package com.example.android_todo_app.database

import android.app.Application
import androidx.room.Room

//アプリ内のデータベースにアクセスするためのオブジェクトを生成
class RoomApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    //アプリが起動した時に呼ばれるデータベースを構築する
    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            //アプリケーション全体で共有されるリソースや情報にアクセスするために使用される
            applicationContext,
            AppDatabase::class.java, "todos"
        ).build()
    }
}