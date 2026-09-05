package com.example.todolistapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.data.entity.TodoEntity
import com.example.todolistapp.databinding.ItemTodoBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TodoAdapter(
    private val onToggleComplete: (TodoEntity) -> Unit,
    private val onDelete: (TodoEntity) -> Unit,
    private val onEdit: (TodoEntity) -> Unit
) : ListAdapter<TodoEntity, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodoViewHolder(binding, onToggleComplete, onDelete, onEdit)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }

    class TodoViewHolder(
        private val binding: ItemTodoBinding,
        private val onToggleComplete: (TodoEntity) -> Unit,
        private val onDelete: (TodoEntity) -> Unit,
        private val onEdit: (TodoEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: TodoEntity) {
            binding.apply {
                todoTitle.text = todo.title
                todoDescription.text = todo.description
                todoCheckbox.isChecked = todo.isCompleted

                // Set priority color
                val priorityColor = when (todo.priority) {
                    "HIGH" -> "🔴"
                    "MEDIUM" -> "🟡"
                    "LOW" -> "🟢"
                    else -> "🟡"
                }
                todoPriority.text = priorityColor

                // Format date
                val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                val createdDate = dateFormat.format(Date(todo.createdAt))
                todoDate.text = createdDate

                // Checkbox listener
                todoCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    onToggleComplete(todo)
                }

                // Delete button
                deleteBtn.setOnClickListener {
                    onDelete(todo)
                }

                // Edit button
                editBtn.setOnClickListener {
                    onEdit(todo)
                }
            }
        }
    }

    class TodoDiffCallback : DiffUtil.ItemCallback<TodoEntity>() {
        override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem == newItem
        }
    }
}
