package com.example.todo_app.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_app.entities.TodoModel
import com.example.todo_app.repos.TodoRepository
import com.example.todo_app.workmanagerutils.WorkManagerService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: TodoRepository): ViewModel() {

    fun insertTodo(todoModel: TodoModel) {
        viewModelScope.launch {
            repository.insertTodo(todoModel)
        }
    }

    fun fetchAllTodo(): LiveData<List<TodoModel>> {
        return repository.getAllTodos()
    }

    fun updateTodo(todoModel: TodoModel) {
        todoModel.isCompleted = !todoModel.isCompleted
        viewModelScope.launch {
            repository.updateTodo(todoModel)
        }
    }

    fun deleteTodo(todoModel: TodoModel) {
        viewModelScope.launch {
            repository.deleteTodo(todoModel)
        }
    }

    fun scheduleNotification(context: Context, name: String, delay: Long) {
        WorkManagerService(context).schedule(name, delay)
    }
}