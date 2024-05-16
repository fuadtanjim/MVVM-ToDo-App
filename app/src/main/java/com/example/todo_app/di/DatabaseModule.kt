package com.example.todo_app.di

import android.content.Context
import com.example.todo_app.daos.TodoDao
import com.example.todo_app.db.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideTodoDao(@ApplicationContext context: Context): TodoDao {
       return TodoDatabase.getDB(context).getTodoDao()
    }
}