package com.aleem.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() {

    private val todoRepository = TodoRepository()

    fun fetchTodos(): LiveData<Result<TodoModel>> {
        return todoRepository.getTodos()
    }

    fun addTodo(todoItem: TodoItem) {
        return todoRepository.addTodo(todoItem)

    }

    fun updateTodo(id: String, updateTodoRequest: UpdateTodoRequest): LiveData<Result<TodoItem>> {
       return todoRepository.updateData(id,updateTodoRequest)

    }



}