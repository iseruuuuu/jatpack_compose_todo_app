package com.example.android_todo_app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import com.example.android_todo_app.database.RoomApplication
import com.example.android_todo_app.model.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel {

    val dao = RoomApplication.database.todoDao()
    var todoList = mutableStateListOf<Todo>()

    fun loadTodo() {
        //Jetpack ComposeとKotlin Coroutinesを組み合わせて、非同期処理を行っている。
        //Coroutines・・・非同期処理や並行処理を簡単かつ効率的に行うための軽量なスレッドライク
        //Flutterで言う、Futureとasync/await構文

        //CoroutineScopeは、ライフサイクルを管理するためのクラス
        CoroutineScope(Dispatchers.Main).launch {
            //withContextは、コルーチンのContextを一時的に切り替える関数
            withContext(Dispatchers.Default) {
                dao.getAll().forEach { todo ->
                    todoList.add(todo)
                }
            }
        }
    }

    fun postTodo(title: String) {
        //UIの状態変化(MainとDefaultの位置が逆になるとエラーになる)
        CoroutineScope(Dispatchers.Main).launch {
            //バックグラウンドの状態変化
            withContext(Dispatchers.Default) {
                val id = Math.random()
                dao.post(Todo(title = title, id = id.toLong()))
                todoList.clear()
                loadTodo()
            }
        }
    }

    fun deleteTodo(todo: Todo) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.delete(todo)
                todoList.clear()
                loadTodo()
            }
        }
    }
}