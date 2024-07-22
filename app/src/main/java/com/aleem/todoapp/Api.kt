package com.aleem.todoapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {

    @GET("/todos")
//    suspend fun fetchTodos() : Response<Todos>

    fun fetchTodos(): Call<TodoModel>

    @POST("todos/add")
    fun addTodo(@Body todo: TodoItem): Call<TodoItem>

    @PUT("todos/{id}")
    fun updateTodo(
        @Path("id") id: String,
        @Body updateData: UpdateTodoRequest
    ): Call<TodoItem>

}