# Todo List App with Local Storage

A modern Android to-do list application with persistent local storage, task management, filtering, and priority levels.

## Features

✅ **Create, Read, Update, Delete (CRUD)** - Full todo management capabilities
✅ **Local Storage** - Uses Room database for persistent data storage
✅ **Task Filtering** - View all, active, or completed tasks
✅ **Priority Levels** - Set tasks as HIGH, MEDIUM, or LOW priority
✅ **Task Completion Toggle** - Mark tasks as complete/incomplete
✅ **Task Descriptions** - Add detailed descriptions to each task
✅ **Date Tracking** - Automatically tracks creation date for each task
✅ **Bulk Operations** - Clear all completed tasks at once
✅ **Edit Tasks** - Modify task title, description, and priority
✅ **User-Friendly UI** - Clean and intuitive Material Design interface

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite)
- **Concurrency**: Coroutines & Flow
- **UI Framework**: AndroidX & Material Design 3
- **Data Binding**: View Binding
- **Testing**: JUnit & Espresso

## Project Structure

```
app/src/main/
├── java/com/example/todolistapp/
│   ├── data/
│   │   ├── database/
│   │   │   ├── TodoDatabase.kt       # Room database configuration
│   │   │   └── TodoDao.kt            # Database access object
│   │   ├── entity/
│   │   │   └── TodoEntity.kt         # Todo data model
│   │   └── repository/
│   │       └── TodoRepository.kt     # Data access layer
│   ├── viewmodel/
│   │   └── TodoViewModel.kt          # Business logic & state management
│   └── ui/
│       ├── MainActivity.kt           # Main activity
���       └── adapter/
│           └── TodoAdapter.kt        # RecyclerView adapter
├── res/
│   ├── layout/
│   │   ├── activity_main.xml         # Main screen layout
│   │   ├── item_todo.xml             # Todo list item layout
│   │   └── dialog_add_todo.xml       # Add/edit todo dialog
│   ├── drawable/
│   │   └── item_background.xml       # Item background styling
│   └── values/
│       ├── strings.xml               # String resources
│       └── themes.xml                # App theming
└── AndroidManifest.xml               # App configuration
```

## Installation

### Prerequisites
- Android Studio (latest version)
- Android SDK 21 or higher
- Kotlin 2.2+
- Java 17+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/Homny123/todo-list-app-local-storage.git
   cd todo-list-app-local-storage
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Click "Open an existing project"
   - Navigate to the cloned repository

3. **Build the project**
   - Click "Build" > "Make Project"
   - Wait for dependencies to download

4. **Run the app**
   - Click "Run" > "Run 'app'"
   - Select an emulator or physical device
   - The app will install and launch

## Usage

### Add a Todo
1. Click the "+ Add" button in the top right
2. Enter the todo title (required)
3. Add an optional description
4. Select priority level (HIGH, MEDIUM, LOW)
5. Click "Save"

### Mark Todo as Complete
- Check the checkbox next to the todo
- The todo will be marked as completed
- Uncheck to mark as incomplete

### Edit a Todo
1. Click the "Edit" button on the todo item
2. Modify the title, description, or priority
3. Click "Save" to apply changes

### Delete a Todo
- Click the "Delete" button on the todo item
- The todo will be removed immediately

### Filter Todos
- Click "All" to view all todos
- Click "Active" to view only incomplete todos
- Click "Completed" to view only completed todos

### Clear Completed Todos
- Click "Clear Completed" at the bottom
- All completed todos will be deleted

## Data Models

### TodoEntity
```kotlin
data class TodoEntity(
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val dueDate: Long? = null,
    val priority: String = "MEDIUM"
)
```

## Database Schema

**Table: todos**
| Column | Type | Properties |
|--------|------|------------|
| id | INTEGER | PRIMARY KEY, AUTO INCREMENT |
| title | TEXT | NOT NULL |
| description | TEXT | |
| isCompleted | BOOLEAN | DEFAULT 0 |
| createdAt | LONG | DEFAULT current time |
| dueDate | LONG | NULLABLE |
| priority | TEXT | DEFAULT 'MEDIUM' |

## API Reference

### TodoRepository Methods

```kotlin
// Get all todos
fun getAllTodos(): Flow<List<TodoEntity>>

// Get active (incomplete) todos
fun getActiveTodos(): Flow<List<TodoEntity>>

// Get completed todos
fun getCompletedTodos(): Flow<List<TodoEntity>>

// Get todos by priority
fun getTodosByPriority(priority: String): Flow<List<TodoEntity>>

// Get todo by ID
suspend fun getTodoById(id: Int): TodoEntity?

// Insert new todo
suspend fun insertTodo(todo: TodoEntity): Long

// Update existing todo
suspend fun updateTodo(todo: TodoEntity)

// Delete todo
suspend fun deleteTodo(todo: TodoEntity)

// Delete todo by ID
suspend fun deleteTodoById(id: Int)

// Delete all completed todos
suspend fun deleteCompletedTodos()
```

## ViewModel Methods

```kotlin
// Add a new todo
fun addTodo(title: String, description: String = "", priority: String = "MEDIUM")

// Update existing todo
fun updateTodo(todo: TodoEntity)

// Toggle todo completion status
fun toggleTodoCompletion(todo: TodoEntity)

// Delete a todo
fun deleteTodo(todo: TodoEntity)

// Delete all completed todos
fun deleteCompletedTodos()

// Set current filter
fun setFilter(filter: String)
```

## Best Practices Implemented

✅ **MVVM Architecture** - Separation of concerns with ViewModel handling business logic
✅ **Room Database** - Type-safe database access with compile-time verification
✅ **Coroutines** - Asynchronous operations without blocking the UI thread
✅ **Flow API** - Reactive data streams for real-time UI updates
✅ **LiveData** - Observable data holder that respects lifecycle
✅ **Repository Pattern** - Abstract data access layer
✅ **DiffUtil** - Efficient RecyclerView updates
✅ **Material Design** - Modern UI/UX principles
✅ **Null Safety** - Kotlin's type safety and null handling
✅ **Resource Management** - Proper cleanup and lifecycle handling

## Future Enhancements

- [ ] Add recurring tasks
- [ ] Implement reminders/notifications
- [ ] Add due date picker
- [ ] Implement task categories/tags
- [ ] Add search functionality
- [ ] Implement dark mode toggle
- [ ] Add data backup/export
- [ ] Implement sync with cloud storage
- [ ] Add widget support
- [ ] Multi-language support

## Troubleshooting

### Database Not Found
- Clear app cache: Settings > Apps > Todo List App > Storage > Clear Cache
- Reinstall the app

### UI Not Updating
- Ensure ViewModel is properly initialized
- Check that observables are set up in onCreate()
- Verify coroutines are running on the correct dispatcher

### Todos Disappearing
- This should not happen with Room database
- If it does, check Room migration or database upgrade issues
- Contact support with reproduction steps

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, email support@example.com or open an issue on GitHub.

## Acknowledgments

- Android Architecture Components
- Material Design Guidelines
- Android Development Community

---

**Made with ❤️ by Homny123**
