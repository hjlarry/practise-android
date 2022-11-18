package com.commonsware.todo.ui.roster

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commonsware.todo.repo.FilterMode
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.repo.ToDoRepository
import com.commonsware.todo.report.RosterReport
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RosterMotor(private val repo: ToDoRepository, private val report: RosterReport) :
    ViewModel() {
    private val _states = MutableStateFlow(RosterViewState())
    val states = _states.asStateFlow()
    private var job: Job? = null
    private val _navEvents = MutableSharedFlow<Nav>()
    val navEvents = _navEvents.asSharedFlow()

    init {
        load(FilterMode.ALL)
    }

    fun load(filterMode: FilterMode) {
        job?.cancel()
        job = viewModelScope.launch {
            repo.items(filterMode).collect {
                _states.emit(RosterViewState(it, true, filterMode))
            }
        }
    }

    fun save(model: ToDoModel) {
        viewModelScope.launch {
            repo.save(model)
        }
    }

    fun saveReport(doc: Uri) {
        viewModelScope.launch {
            report.generate(_states.value.items, doc)
            _navEvents.emit(Nav.ViewReport(doc))
        }
    }
}

data class RosterViewState(
    val items: List<ToDoModel> = listOf(),
    val isLoaded: Boolean = false,
    val filterMode: FilterMode = FilterMode.ALL
)

sealed class Nav {
    data class ViewReport(val doc: Uri) : Nav()
}