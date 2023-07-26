package com.johndev.notebook.di

import android.content.Context
import androidx.room.Room
import com.johndev.notebook.core.local.FolderDao
import com.johndev.notebook.core.local.NoteDao
import com.johndev.notebook.core.local.NoteDatabase
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
            .addMigrations(NoteDatabase.MIGRATION_1_TO_2)
            .build()
}