package com.commonsware.todo.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class ToDoRepository(
    private val store: ToDoEntity.Store,
    private val appScope: CoroutineScope,
    private val remoteDateSource: ToDoRemoteDateSource
) {
    var items = emptyList<ToDoModel>()

    suspend fun save(model: ToDoModel) {
        withContext(appScope.coroutineContext) {
            store.save(ToDoEntity(model))
        }
    }

    suspend fun delete(model: ToDoModel) {
        withContext(appScope.coroutineContext) {
            store.delete(ToDoEntity(model))
        }
    }

    private fun filteredEntities(filterMode: FilterMode) = when (filterMode) {
        FilterMode.ALL -> store.all()
        FilterMode.COMPLETED -> store.filtered(isCompleted = true)
        FilterMode.OUTSTANDING -> store.filtered(isCompleted = false)
    }

    fun items(filterMode: FilterMode): Flow<List<ToDoModel>> =
        filteredEntities(filterMode).map { all -> all.map { it.toModel() } }.onStart { delay(1000) }

    fun find(modelId: String?): Flow<ToDoModel?> {
        return store.find(modelId).map { it?.toModel() }
    }

    suspend fun importItems(url: String) {
        withContext(appScope.coroutineContext) {
            store.importItems(remoteDateSource.load(url).map { it.toEntity() })
        }
    }
}

enum class FilterMode { ALL, OUTSTANDING, COMPLETED }