package com.example.android_todo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_todo_app.ui.theme.Android_todo_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_todo_appTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    //リストに動的に要素を追加できるようにする
    val todoList: SnapshotStateList<String> by remember { mutableStateOf(mutableStateListOf()) }
    var text: String by remember { mutableStateOf("") }

    Column {
        TopAppBar(
            title = { Text("Todo List（FlutterでいうAppBar）") }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(todoList) { todo ->
                Text(
                    text = todo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { it -> text = it },
                label = { Text("ToDo") },
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
            )
            Spacer(Modifier.size(16.dp))
            Button(
                onClick = {
                    //ラベル付きリターン文
                    //即座にリターンすることができます。
                    if (text.isEmpty()) return@Button
                    todoList.add(text)
                    text = ""
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("追加")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Android_todo_appTheme {}
}