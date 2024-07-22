package com.aleem.todoapp

import com.google.gson.annotations.SerializedName

data class TodoModel(
    @SerializedName("todos")
    val todos: List<TodoItem>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("limit")
    val limit: Int


)


