package com.aleem.todoapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter : ListAdapter<TodoItem, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoItem = getItem(position)
        holder.bind(todoItem)
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textid: TextView = itemView.findViewById(R.id.tvID)
        private val textTodo: TextView = itemView.findViewById(R.id.tvTodo)
        private val textStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val textUserId: TextView = itemView.findViewById(R.id.tvUserId)

        fun bind(todoItem: TodoItem) {
            textid.text = todoItem.id.toString()
            textTodo.text = todoItem.todo
            textStatus.text = todoItem.completed.toString()
            textUserId.text = todoItem.userId.toString()
        }
    }

    // DiffUtil class for calculating the difference between two lists
    class TodoDiffCallback : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem == newItem
        }
    }
    fun addTodoItem(todoItem: TodoItem) {
        val currentList = currentList.toMutableList()
        currentList.add(todoItem)
        submitList(currentList)
    }

}
