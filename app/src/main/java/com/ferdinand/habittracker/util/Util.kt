package com.ferdinand.habittracker.util

import android.content.Context
import com.ferdinand.habittracker.model.AppDatabase

fun buildDb(context: Context): AppDatabase {
    val db = AppDatabase.buildDatabase(context)
    return db
}