package com.example.task_four_sqlite3

import android.provider.BaseColumns

object TaskContract {
    object TaskEntry : BaseColumns {
        const val TABLE_NAME = "tasks"
        const val COLUMN_ID = BaseColumns._ID
        const val COLUMN_TITLE = "title"
    }
}