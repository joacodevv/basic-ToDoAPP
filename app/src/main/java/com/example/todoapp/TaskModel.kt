package com.example.todoapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "tasks")
data class TaskModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "task_name") val name: String,
    @ColumnInfo(name = "task_description") val description: String
)

