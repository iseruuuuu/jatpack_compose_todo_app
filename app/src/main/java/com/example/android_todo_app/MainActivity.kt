package com.example.android_todo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_todo_app.ui.theme.Android_todo_appTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class MainActivity : ComponentActivity() {

    private val dao = RoomApplication.database.todoDao()
    private var todoList = mutableStateListOf<Todo>()

    //flutterでいうinitState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_todo_appTheme {
                //FlutterでいうScaffold
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(todoList)
                }
            }
        }
        loadTodo()
    }

    private fun loadTodo() {
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

    private fun postTodo(title: String) {
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

    private fun deleteTodo(todo: Todo) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.delete(todo)
                todoList.clear()
                loadTodo()
            }
        }
    }

    @Composable
    fun MainScreen(todoList: SnapshotStateList<Todo>) {
        //リストに動的に要素を追加できるようにする
        var text: String by remember { mutableStateOf("") }
        Column {
            TopAppBar(
                title = { Text("Todo List（FlutterでいうAppBar）") }
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                items(todoList) { todo ->
                    key(todo.id) {
                        TodoItem(todo)
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = {it -> text = it},
                    //onValueChange = { text = it },でもOK
                    label = { Text("ToDo") },
                    modifier = Modifier.wrapContentHeight().weight(1f)
                )
                Spacer(Modifier.size(16.dp))
                Button(
                    onClick = {
                        //ラベル付きリターン文
                        //即座にリターンすることができます。
                        if (text.isEmpty()) return@Button
                        postTodo(text)
                        text = ""
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("追加")
                }
            }
        }
    }

    @Composable
    fun TodoItem(todo: Todo) {
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
            .clickable { deleteTodo(todo) }) {
            Text(
                text = todo.title,
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
            )
            Text(
                text = "created at: ${sdf.format(todo.created_at)}",
                fontSize = 12.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Android_todo_appTheme {}
}