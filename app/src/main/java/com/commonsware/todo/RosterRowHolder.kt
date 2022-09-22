package com.commonsware.todo

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.commonsware.todo.databinding.TodoRowBinding

class RosterRowHolder(private val binding: TodoRowBinding) : ViewHolder(binding.root) {
    fun bind(model: ToDoModel) {
        binding.apply {
            isCompleted.isChecked = model.isCompleted
            desc.text = model.description
        }
    }
}