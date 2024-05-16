package com.example.todo_app.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todo_app.entities.TodoModel

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todoModel: TodoModel)

    @Update
    suspend fun updateTodo(todoModel: TodoModel)

    @Delete
    suspend fun deleteTodo(todoModel: TodoModel)

    @Query("select * from todo order by id desc")
    fun getAllTodos(): LiveData<List<TodoModel>>
}