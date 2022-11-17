package com.commonsware.todo.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

private const val DB_NAME = "stuff.db"

@Database(entities = [ToDoEntity::class], version = 1, exportSchema = false)
@TypeConverters(TypeTransmogrifier::class)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun todoStore(): ToDoEntity.Store

    companion object {
        fun newInstance(context: Context): ToDoDatabase {
            return Room.databaseBuilder(context, ToDoDatabase::class.java, DB_NAME).build()
        }

        fun newTestInstance(context: Context): ToDoDatabase {
            return Room.inMemoryDatabaseBuilder(context, ToDoDatabase::class.java).build()
        }
    }
}