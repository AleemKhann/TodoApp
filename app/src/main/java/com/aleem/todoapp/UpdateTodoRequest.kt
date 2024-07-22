package com.aleem.todoapp

import com.google.gson.annotations.SerializedName

data class UpdateTodoRequest (
    @SerializedName("completed") val completed: Boolean

    )