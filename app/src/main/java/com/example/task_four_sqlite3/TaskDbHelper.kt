package com.example.task_four_sqlite3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class TaskDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "tasks.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val SQL_CREATE_ENTRIES = "CREATE TABLE ${TaskContract.TaskEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TaskContract.TaskEntry.COLUMN_TITLE} TEXT)"

        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addTask(dbHelper: TaskDbHelper, title: String): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TaskContract.TaskEntry.COLUMN_TITLE, title)
        }

        val id = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllTasks(dbHelper: TaskDbHelper): List<String> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(BaseColumns._ID, TaskContract.TaskEntry.COLUMN_TITLE)

        val cursor = db.query(
            TaskContract.TaskEntry.TABLE_NAME,
            projection,
            null, null, null, null, null
        )

        val tasks = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val title = getString(getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_TITLE))
                tasks.add(title)
            }
        }
        cursor.close()
        db.close()
        return tasks
    }
}
