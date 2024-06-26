package com.example.task_four_sqlite3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.task_four_sqlite3.ui.theme.Task_four_sqlite3Theme

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: TaskDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        dbHelper = TaskDbHelper(this@MainActivity)
        setContent {
            Task_four_sqlite3Theme {
                TaskApp(dbHelper)
            }
        }
    }
}

@Composable
fun TaskApp(dbHelper: TaskDbHelper) {
    var tasks by remember { mutableStateOf(emptyList<String>()) }
    var newTaskTitle by remember { mutableStateOf("") }

    tasks = dbHelper.getAllTasks(dbHelper)

    Scaffold(
        modifier = Modifier.padding(16.dp)
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TextField(
                value = newTaskTitle, onValueChange = { newTaskTitle = it },
                label = { Text(text = "New Task") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    dbHelper.addTask(dbHelper, newTaskTitle)
                    tasks = dbHelper.getAllTasks(dbHelper)
                    newTaskTitle = ""
                }),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    dbHelper.addTask(dbHelper, newTaskTitle)
                    tasks = dbHelper.getAllTasks(dbHelper)
                    newTaskTitle = ""
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add Task")
            }
            Spacer(modifier = Modifier.padding(paddingValues))
            TaskList(tasks = tasks)
        }
    }
}

@Composable
fun TaskList(tasks: List<String>) {
    LazyColumn {
        items(tasks) { task ->
            Text(text = task, modifier = Modifier.padding(8.dp))
            HorizontalDivider()
        }
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun DefaultPreview(modifier: Modifier = Modifier) {
    Task_four_sqlite3Theme {
        TaskList(tasks = listOf("Task 1", "Task 2", "Task 3"))
    }
}