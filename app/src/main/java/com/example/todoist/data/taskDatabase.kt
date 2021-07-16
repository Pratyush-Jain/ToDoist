package com.example.todoist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoist.adapter.Category
import com.example.todoist.adapter.Task

@Database(entities = [Task::class, Category::class],version = 7,exportSchema = false)
abstract class taskDatabase:RoomDatabase() {

    abstract fun taskDao():taskDao
    companion object{
        @Volatile
        private var INSTANCE:taskDatabase? = null

        fun getDatabase(context:Context):taskDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            else{
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    taskDatabase::class.java,
                    "task_database"
                )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}