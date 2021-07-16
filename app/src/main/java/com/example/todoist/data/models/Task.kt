package com.example.todoist.adapter

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Priority{
    HIGH,
    MEDIUM,
    LOW,
    High,Medium,Low
}
@Entity(tableName = "task_table")
class Task(
    @PrimaryKey(autoGenerate = true) var id:Long= 0,
        val title:String,
        var category:String ,
        var isCompleted:Boolean = false,
        var priority:Priority = Priority.MEDIUM

){

}

@Entity(tableName = "category")
class Category(

    @PrimaryKey val category:String
){

}
