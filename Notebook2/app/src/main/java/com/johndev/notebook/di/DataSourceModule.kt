package com.johndev.notebook.di

import com.johndev.notebook.core.local.FolderDataSource
import com.johndev.notebook.core.local.FolderDataSourceRoom
import com.johndev.notebook.core.local.NoteDataSource
import com.johndev.notebook.core.local.NoteDataSourceRoom
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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