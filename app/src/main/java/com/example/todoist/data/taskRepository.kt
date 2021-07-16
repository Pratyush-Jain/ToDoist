package com.example.todoist.data

import androidx.lifecycle.LiveData
import com.example.todoist.POJO.CategTaskCount
import com.example.todoist.adapter.Category
import com.example.todoist.adapter.Task

class taskRepository(private val taskDao: taskDao) {



    val getCategories:Array<String> = taskDao.getCategories()


    //val readAllCategoryTaskCount: LiveData<List<CategTaskCount>> = taskDao.getAllCategoryTaskCount()
    val getAllCategory: LiveData<List<CategTaskCount>> = taskDao.getAllCategory()
    //val getCategories: List<String> = taskDao.getCategories()
    suspend fun UpdateTaskCompletion(id: Long,isComplete:Boolean){
        taskDao.UpdateTaskCompletion(id,isComplete)
    }
     fun readAllTasks(categ:String):LiveData<MutableList<Task>>{

        return taskDao.readCategTasks(categ)
    }


    suspend fun addTask(task:Task){
        taskDao.addTask(task)
    }


    suspend fun addCategory(category:Category){
        taskDao.addCategory(category)
    }

    fun readAllTasksNonLive(categ: String): MutableList<Task> {
        return taskDao.readCategTasksNotLive(categ)
    }

    suspend fun updateTask(updatedTask: Task) {
        taskDao.updateTask(updatedTask)

    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
//    fun getCategories():Array<String>{
//        return taskDao.getCategories()
//    }




}