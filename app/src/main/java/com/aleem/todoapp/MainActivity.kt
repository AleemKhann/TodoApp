package com.aleem.todoapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private val viewModel: TodoViewModel by viewModels()
    private lateinit var adapter: TodoAdapter
    private var recycler_view_tasks: RecyclerView? = null
    private var mAddFab: FloatingActionButton? = null
    private var mEditFab: FloatingActionButton? = null
    private var mUpdateFab: FloatingActionButton? = null
    private lateinit var createFabText: TextView
    private lateinit var updateActionText: TextView

    private var isAllFabsVisible: Boolean? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view_tasks = findViewById(R.id.recycler_view_tasks)

        mAddFab = findViewById(R.id.add_fab)

        mEditFab = findViewById(R.id.create_todo_fab)
        mUpdateFab = findViewById(R.id.update_todo_fab)

        createFabText = findViewById(R.id.create_todo_action_text)
        updateActionText = findViewById(R.id.update_todo_action_text)

        // Now set all the FABs and all the action name texts as GONE
        mEditFab?.visibility = View.GONE
        mUpdateFab?.visibility = View.GONE
        createFabText.visibility = View.GONE
        updateActionText.visibility = View.GONE
        isAllFabsVisible = false
        mAddFab?.setOnClickListener(View.OnClickListener {
            (if (!isAllFabsVisible!!) {
                mEditFab?.show()
                mUpdateFab?.show()
                createFabText.visibility = View.VISIBLE
                updateActionText.visibility = View.VISIBLE
                true
            } else {
                mEditFab?.hide()
                mUpdateFab?.hide()
                createFabText.visibility = View.GONE
                updateActionText.visibility = View.GONE
                false
            }).also { isAllFabsVisible = it }
        })
        mUpdateFab?.setOnClickListener {
            Toast.makeText(this, "Todo Updated", Toast.LENGTH_SHORT).show()
        }
        mEditFab?.setOnClickListener {
            showAlertDialogButtonClicked()
            Toast.makeText(this, "Todo Created", Toast.LENGTH_SHORT).show()
        }
        adapter = TodoAdapter()
        recycler_view_tasks?.layoutManager = LinearLayoutManager(this)
        recycler_view_tasks?.adapter = adapter

        getTodoFromViewModel()

        mUpdateFab?.setOnClickListener {

            val todoId = "2" // Replace with actual todo ID
            val updateRequest = UpdateTodoRequest(true)

            viewModel.updateTodo(todoId, updateRequest).observe(this, Observer { result ->

                if (result.isSuccess) {
                    val updatedTodo = todoId
                    //change UI as per requirments
                    Log.e("MainActivity", "Todo updated: $updatedTodo")
                    Toast.makeText(this, "Todo updated: $updatedTodo", Toast.LENGTH_SHORT).show()

                }
                if (result.isFailure) {
                    Log.e("MainActivity", "Failed to update todo: ${error("error")}}")
                }
            })
        }

    }


    private fun showAlertDialogButtonClicked() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Create Todos")
        val customLayout: View = layoutInflater.inflate(R.layout.edit_dialog, null)
        builder.setView(customLayout)
        builder.setPositiveButton("OK") { dialog, which ->
//            val evId = customLayout.findViewById<EditText>(R.id.evCreateId)
//            val evTodos = customLayout.findViewById<EditText>(R.id.evTodos)
//            val evStatus = customLayout.findViewById<EditText>(R.id.evCompleted)
//            val evUserId = customLayout.findViewById<EditText>(R.id.evUserId)

            val evId = customLayout.findViewById<EditText>(R.id.evCreateId)
            val evTodos = customLayout.findViewById<EditText>(R.id.evTodos)
            val evStatus = customLayout.findViewById<EditText>(R.id.evCompleted)
            val evUserId = customLayout.findViewById<EditText>(R.id.evUserId)


            try {
                val id = Integer.parseInt(evId.text.toString())
                val status = evStatus.text.toString().toBoolean()
                val userId = Integer.parseInt(evUserId.text.toString())

                if (id.toString().isNotEmpty() && evStatus.toString()
                        .isNotEmpty() && status.toString().isNotEmpty() && userId.toString()
                        .isNotEmpty()
                ) {


                    val newT: TodoItem = TodoItem(id, evTodos.text.toString(), status, userId)
                    Log.e("checkboolean", newT.toString())

                    sendDialogDataToActivity(id, evTodos.text.toString(), status, userId)
                } else {
                    Toast.makeText(this, "fill the field", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun sendDialogDataToActivity(id: Int, todo: String, status: Boolean, userId: Int) {

        val newT = TodoItem(id, todo, status, userId)
        adapter.addTodoItem(newT)
        getTodoFromViewModel()
        Toast.makeText(this, newT.toString(), Toast.LENGTH_SHORT).show()
        Log.e("ccccccc", newT.toString())
    }


    fun getTodoFromViewModel() {
        viewModel.fetchTodos().observe(this, Observer { result ->
            result.onSuccess { todoResponse ->
                // Update UI with fetched todos
                adapter.submitList(todoResponse.todos)
                Log.e("TodoListActivity", "Todo items: ${todoResponse.todos}")
            }
            result.onFailure { error ->
                error.printStackTrace()
            }
        })
    }
}