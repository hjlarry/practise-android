package com.commonsware.todo.ui.roster

import androidx.lifecycle.ViewModel
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.repo.ToDoRepository

class RosterMotor(private val repo: ToDoRepository) : ViewModel() {
    fun save(model: ToDoModel) {
        repo.save(model)
    }

    fun getItems() = repo.items
}