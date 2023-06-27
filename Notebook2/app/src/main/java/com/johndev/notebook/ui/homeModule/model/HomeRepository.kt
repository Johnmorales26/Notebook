package com.johndev.notebook.ui.homeModule.model

import com.johndev.notebook.core.local.FolderDataSource
import com.johndev.notebook.core.local.NoteDataSource
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val folderDataSource: FolderDataSource
) {

    fun getAllNotes(onSuccess: (Flow<List<NoteEntity>>) -> Unit) = noteDataSource.getAll(callback = {
        onSuccess(it)
    })

    fun getAllFolders(onSuccess: (Flow<List<FolderEntity>>) -> Unit) = folderDataSource.getAll(callback = {
        onSuccess(it)
    })

    fun getFoldersByName(name: String, onSuccess: (Flow<List<NoteEntity>>) -> Unit) =
        noteDataSource.findByName(title = name, callback = {
            onSuccess(it)
        })

    fun getFolderByName(name: String, onSuccess: (FolderEntity?) -> Unit) {
        folderDataSource.getByName(name = name, callback = {
            onSuccess(it)
        })
    }

    fun insert(folderEntity: FolderEntity, onSuccess: (Long) -> Unit) =
        folderDataSource.insert(folderEntity, callback = {
            onSuccess(it)
        })

    fun delete(folderEntity: FolderEntity) = folderDataSource.delete(folderEntity)

    fun update(folderEntity: FolderEntity) = folderDataSource.update(folderEntity)

}