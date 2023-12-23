package com.taskmananger.repo

import com.taskmananger.database.DatabaseManager
import com.taskmananger.entities.ToDo
import com.taskmananger.entities.ToDoDraft

class DBToDoRepository : ToDoRepository {
    private val database = DatabaseManager()
    override fun getAllToDos(): List<ToDo> {
        return database.getAllTodos()
            .map { ToDo(it.id, it.title, it.done) }
    }

    override fun getToDo(id: Int): ToDo? {
        return database.getTodo(id)
            ?.let { ToDo(it.id, it.title, it.done) }
    }

    override fun addToDo(draft: ToDoDraft): ToDo {
        return database.addTodo(draft)
    }

    override fun removeToDo(id: Int): Boolean {
        return database.removeTodo(id)
    }

    override fun updateToDo(id: Int, draft: ToDoDraft): Boolean {
        return database.updateTodo(id, draft)
    }
}