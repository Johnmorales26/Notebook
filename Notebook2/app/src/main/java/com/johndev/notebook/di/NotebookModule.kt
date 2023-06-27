package com.johndev.notebook.di

import android.content.Context
import androidx.room.Room
import com.johndev.notebook.core.local.FolderDao
import com.johndev.notebook.core.local.FolderDataSource
import com.johndev.notebook.core.local.FolderDataSourceRoom
import com.johndev.notebook.core.local.NoteDao
import com.johndev.notebook.core.local.NoteDataSource
import com.johndev.notebook.core.local.NoteDataSourceRoom
import com.johndev.notebook.core.local.NoteDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao = database.noteDao()

    @Provides
    fun provideFolderDao(database: NoteDatabase): FolderDao = database.folderDao()

    @Singleton
    @Provides
    fun provideDatabaseRoom(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, "NoteDatabase")
            .build()

}

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindNoteDataSource(impl: NoteDataSourceRoom): NoteDataSource

    @Singleton
    @Binds
    abstract fun bindFolderDataSource(impl: FolderDataSourceRoom): FolderDataSource

}