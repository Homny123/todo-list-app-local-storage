package com.example.todolistapp.data.repository

import com.example.todolistapp.data.database.TodoDao
import com.example.todolistapp.data.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    fun getAllTodos(): Flow<List<TodoEntity>> = todoDao.getAllTodos()

    fun getActiveTodos(): Flow<List<TodoEntity>> = todoDao.getActiveTodos()

    fun getCompletedTodos(): Flow<List<TodoEntity>> = todoDao.getCompletedTodos()

    fun getTodosByPriority(priority: String): Flow<List<TodoEntity>> =
        todoDao.getTodosByPriority(priority)

    suspend fun getTodoById(id: Int): TodoEntity? = todoDao.getTodoById(id)

    suspend fun insertTodo(todo: TodoEntity): Long = todoDao.insertTodo(todo)

    suspend fun updateTodo(todo: TodoEntity) = todoDao.updateTodo(todo)

    suspend fun deleteTodo(todo: TodoEntity) = todoDao.deleteTodo(todo)

    suspend fun deleteTodoById(id: Int) = todoDao.deleteTodoById(id)

    suspend fun deleteCompletedTodos() = todoDao.deleteCompletedTodos()
}
