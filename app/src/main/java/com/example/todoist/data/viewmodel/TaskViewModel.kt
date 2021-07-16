 package com.example.todoist.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoist.POJO.CategTaskCount
import com.example.todoist.adapter.Category
import com.example.todoist.adapter.Task
import com.example.todoist.data.taskDatabase
import com.example.todoist.data.taskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

 class TaskViewModel(applcation: Application):AndroidViewModel(applcation) {
    var readAllTask:LiveData<List<Task>>
    val getCategories:Array<String>
    val getAllCategory: LiveData<List<CategTaskCount>>
    var selectedCateg: MutableLiveData<String> = MutableLiveData<String>()
    private val repository: taskRepository

    init {
        val taskDao = taskDatabase.getDatabase(applcation).taskDao()
        repository = taskRepository(taskDao)
        readAllTask = readAllTasks("")
        getAllCategory = repository.getAllCategory
        getCategories = repository.getCategories

    }

    fun setCateg(categ: String) {
        readAllTask = readAllTasks(categ)

    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }
    fun readAllTasks(categ:String):LiveData<List<Task>> {
        return repository.readAllTasks(categ)
    }

     fun readAllTasksNonLive(categ:String):List<Task> {
         return repository.readAllTasksNonLive(categ)
     }


    fun addCategory(categ: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCategory(categ)
        }
    }

    fun UpdateTaskCompletion(id: Long,isComplete:Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.UpdateTaskCompletion(id,isComplete)
        }
    }

     fun updateTask(updatedTask: Task) {
         viewModelScope.launch(Dispatchers.IO) {
             repository.updateTask(updatedTask)
         }

     }

 }