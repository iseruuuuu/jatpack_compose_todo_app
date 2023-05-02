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
import com.example.android_todo_app.model.Todo
import com.example.android_todo_app.ui.theme.Android_todo_appTheme
import com.example.android_todo_app.viewmodel.ViewModel
import java.text.SimpleDateFormat

class MainActivity : ComponentActivity() {
    private var viewModel = ViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_todo_appTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(viewModel.todoList)
                }
            }
        }
        viewModel.loadTodo()
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
                    onValueChange = { it -> text = it },
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
                        //postTodo(text)
                        viewModel.postTodo(text)
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
                .clickable { viewModel.deleteTodo(todo) }) {
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