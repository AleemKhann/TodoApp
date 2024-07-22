package com.aleem.todoapp

import com.google.gson.annotations.SerializedName

data class TodoItem(

    @SerializedName("id")
    val id: Int,
    @SerializedName("todo")
    var todo: String,
    @SerializedName("completed")
    var completed: Boolean,
    @SerializedName("userId")
    var userId: Int

)