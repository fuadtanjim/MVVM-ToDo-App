package com.example.todo_app.repos

import androidx.lifecycle.LiveData
import com.example.todo_app.daos.TodoDao
import com.example.todo_app.entities.TodoModel
import javax.inject.Inject


class TodoRepository @Inject constructor(private val todoDao: TodoDao) {

    suspend fun insertTodo(todoModel: TodoModel) {
        todoDao.addTodo(todoModel)
    }

    fun getAllTodos(): LiveData<List<TodoModel>> {
        return todoDao.getAllTodos()
    }

    suspend fun updateTodo(todoModel: TodoModel) {
        todoDao.updateTodo(todoModel)
    }

    suspend fun deleteTodo(todoModel: TodoModel) {
        todoDao.deleteTodo(todoModel)
    }
}