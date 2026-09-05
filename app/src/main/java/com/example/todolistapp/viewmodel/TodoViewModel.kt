package com.example.todolistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.database.TodoDatabase
import com.example.todolistapp.data.entity.TodoEntity
import com.example.todolistapp.data.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TodoRepository
    val allTodos: LiveData<List<TodoEntity>>
    val activeTodos: LiveData<List<TodoEntity>>
    val completedTodos: LiveData<List<TodoEntity>>

    private val _currentFilter = MutableLiveData<String>()
    val currentFilter: LiveData<String> get() = _currentFilter

    init {
        val todoDao = TodoDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(todoDao)
        allTodos = repository.getAllTodos().asLiveData()
        activeTodos = repository.getActiveTodos().asLiveData()
        completedTodos = repository.getCompletedTodos().asLiveData()
    }

    fun addTodo(title: String, description: String = "", priority: String = "MEDIUM") {
        viewModelScope.launch {
            val newTodo = TodoEntity(
                title = title,
                description = description,
                priority = priority
            )
            repository.insertTodo(newTodo)
        }
    }

    fun updateTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun toggleTodoCompletion(todo: TodoEntity) {
        viewModelScope.launch {
            val updatedTodo = todo.copy(isCompleted = !todo.isCompleted)
            repository.updateTodo(updatedTodo)
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }

    fun deleteCompletedTodos() {
        viewModelScope.launch {
            repository.deleteCompletedTodos()
        }
    }

    fun setFilter(filter: String) {
        _currentFilter.value = filter
    }
}
