package com.commonsware.todo.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ToDoRepository(private val store: ToDoEntity.Store, private val appScope: CoroutineScope) {
    var items = emptyList<ToDoModel>()

    suspend fun save(model: ToDoModel) {
        withContext(appScope.coroutineContext){
            store.save(ToDoEntity(model))
        }
    }

    suspend fun delete(model: ToDoModel) {
        withContext(appScope.coroutineContext){
            store.delete(ToDoEntity(model))
        }
    }

    fun items(): Flow<List<ToDoModel>> = store.all().map { all -> all.map { it.toModel() } }

    fun find(modelId: String?): Flow<ToDoModel> {
        return store.find(modelId).map { it.toModel() }
    }
}