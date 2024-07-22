package com.aleem.todoapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TodoRepository {
    private val apiService = RetrofitHelper.apiService

    fun getTodos(): LiveData<Result<TodoModel>> {

        val data = MutableLiveData<Result<TodoModel>>()

        apiService.fetchTodos().enqueue(object : Callback<TodoModel> {
            override fun onResponse(call: Call<TodoModel>, response: Response<TodoModel>) {
                if (response.isSuccessful) {
                    data.value = Result.success(response.body()!!)
                } else {
                    data.value = Result.failure(Exception("Failed to fetch todos"))
                }
            }

            override fun onFailure(call: Call<TodoModel>, t: Throwable) {
                data.value = Result.failure(t)
            }
        })

        return data
    }

    fun addTodo(todoItem: TodoItem) {
        RetrofitHelper.apiService.addTodo(todoItem).enqueue(object : Callback<TodoItem> {
            override fun onResponse(call: Call<TodoItem>, response: Response<TodoItem>) {
                if (response.isSuccessful) {
                    val addedTodo = response.body()
                    Log.e("addedTODO", addedTodo.toString())

                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("errorBody", errorBody.toString())
                }
            }

            override fun onFailure(call: Call<TodoItem>, t: Throwable) {
                t.printStackTrace()
            }
        })


    }


    fun updateData(id: String, updateTodoRequest: UpdateTodoRequest): LiveData<Result<TodoItem>> {
        val resultLiveData = MediatorLiveData<Result<TodoItem>>()

        RetrofitHelper.apiService.updateTodo(id, updateTodoRequest)
            .enqueue(object : Callback<TodoItem> {
                override fun onResponse(call: Call<TodoItem>, response: Response<TodoItem>) {
                    if (response.isSuccessful) {
                        val updatedTodo = response.body()
                        if (updatedTodo != null) {
                            resultLiveData.value = Result.success(updatedTodo)
                            Log.e("addedTODO", updatedTodo.toString())
                        } else {
                            resultLiveData.value =
                                Result.failure(Exception("Response body is null"))

                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("errorBody", errorBody.toString())
                    }

                }

                override fun onFailure(call: Call<TodoItem>, t: Throwable) {
                    Log.e("error", t.message.toString())
                }

            })

        return resultLiveData
    }


}

