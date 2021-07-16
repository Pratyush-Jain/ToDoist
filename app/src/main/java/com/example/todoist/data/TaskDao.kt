package com.example.todoist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoist.POJO.CategTaskCount
import com.example.todoist.adapter.Category
import com.example.todoist.adapter.Task

@Dao
interface taskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task:Task)

//    @Query("SELECT * from (SELECT * FROM task_table ORDER BY CASE priority " +
//            "WHEN 'High' THEN 1 " +
//            "WHEN 'Medium' THEN 2 " +
//            "WHEN 'Low' THEN 3 " +
//            "END ) ORDER BY isCompleted")
//     fun readAllTasks():LiveData<List<Task>>

    @Query("SELECT * from (SELECT * FROM task_table ORDER BY CASE priority " +
            "WHEN 'High' THEN 1 " +
            "WHEN 'Medium' THEN 2 " +
            "WHEN 'Low' THEN 3 " +
            "END ) WHERE category=:Categ ORDER BY isCompleted")
    fun readCategTasks(Categ: String):LiveData<MutableList<Task>>

    @Query("SELECT * from (SELECT * FROM task_table ORDER BY CASE priority " +
            "WHEN 'High' THEN 1 " +
            "WHEN 'Medium' THEN 2 " +
            "WHEN 'Low' THEN 3 " +
            "END ) WHERE category=:Categ ORDER BY isCompleted")
    fun readCategTasksNotLive(Categ: String):MutableList<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategory(category: Category)

//    @Query("SELECT COUNT(category),category FROM task_table GROUP BY category")
//    fun getAllCategoryTaskCount(): LiveData<List<CategTaskCount>>
    @Query("SELECT category FROM Category")
    fun getAllCategory(): LiveData<List<CategTaskCount>>

    @Query("SELECT category FROM Category")
     fun getCategories(): Array<String>

    @Query("UPDATE task_table SET isCompleted = :isComplete WHERE id = :id")
    suspend fun UpdateTaskCompletion(id: Long,isComplete:Boolean)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

}
