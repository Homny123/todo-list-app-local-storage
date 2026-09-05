package com.example.todolistapp.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.R
import com.example.todolistapp.data.entity.TodoEntity
import com.example.todolistapp.databinding.ActivityMainBinding
import com.example.todolistapp.ui.adapter.TodoAdapter
import com.example.todolistapp.viewmodel.TodoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter
    private var editingTodo: TodoEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        // Setup RecyclerView
        setupRecyclerView()

        // Setup Observers
        setupObservers()

        // Setup Button Listeners
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = TodoAdapter(
            onToggleComplete = { todo ->
                viewModel.toggleTodoCompletion(todo)
            },
            onDelete = { todo ->
                viewModel.deleteTodo(todo)
                Toast.makeText(this, "Todo deleted", Toast.LENGTH_SHORT).show()
            },
            onEdit = { todo ->
                showAddEditDialog(todo)
            }
        )
        binding.todosRecyclerView.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupObservers() {
        viewModel.allTodos.observe(this) { todos ->
            adapter.submitList(todos)
            if (todos.isEmpty()) {
                binding.emptyStateText.text = "No todos yet. Add one to get started!"
            }
        }
    }

    private fun setupListeners() {
        binding.addTodoBtn.setOnClickListener {
            showAddEditDialog(null)
        }

        binding.filterAllBtn.setOnClickListener {
            viewModel.allTodos.observe(this) { todos ->
                adapter.submitList(todos)
            }
            Toast.makeText(this, "Showing all todos", Toast.LENGTH_SHORT).show()
        }

        binding.filterActiveBtn.setOnClickListener {
            viewModel.activeTodos.observe(this) { todos ->
                adapter.submitList(todos)
            }
            Toast.makeText(this, "Showing active todos", Toast.LENGTH_SHORT).show()
        }

        binding.filterCompletedBtn.setOnClickListener {
            viewModel.completedTodos.observe(this) { todos ->
                adapter.submitList(todos)
            }
            Toast.makeText(this, "Showing completed todos", Toast.LENGTH_SHORT).show()
        }

        binding.clearCompletedBtn.setOnClickListener {
            viewModel.deleteCompletedTodos()
            Toast.makeText(this, "Cleared completed todos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAddEditDialog(todoToEdit: TodoEntity?) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_todo)
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val titleInput = dialog.findViewById<EditText>(R.id.titleInput)
        val descriptionInput = dialog.findViewById<EditText>(R.id.descriptionInput)
        val prioritySpinner = dialog.findViewById<Spinner>(R.id.prioritySpinner)
        val saveBtn = dialog.findViewById<Button>(R.id.saveBtn)
        val cancelBtn = dialog.findViewById<Button>(R.id.cancelBtn)

        if (todoToEdit != null) {
            titleInput.setText(todoToEdit.title)
            descriptionInput.setText(todoToEdit.description)
            val priorityIndex = when (todoToEdit.priority) {
                "HIGH" -> 0
                "MEDIUM" -> 1
                "LOW" -> 2
                else -> 1
            }
            prioritySpinner.setSelection(priorityIndex)
            editingTodo = todoToEdit
        }

        saveBtn.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()
            val priority = prioritySpinner.selectedItem.toString()

            if (title.isEmpty()) {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (editingTodo != null) {
                val updated = editingTodo!!.copy(
                    title = title,
                    description = description,
                    priority = priority
                )
                viewModel.updateTodo(updated)
                Toast.makeText(this, "Todo updated", Toast.LENGTH_SHORT).show()
                editingTodo = null
            } else {
                viewModel.addTodo(title, description, priority)
                Toast.makeText(this, "Todo added", Toast.LENGTH_SHORT).show()
            }

            dialog.dismiss()
        }

        cancelBtn.setOnClickListener {
            editingTodo = null
            dialog.dismiss()
        }

        dialog.show()
    }
}
