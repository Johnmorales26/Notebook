package com.johndev.notebook.ui.folderModule.model

import com.johndev.notebook.NotesApplication
import com.johndev.notebook.core.local.FolderDao
import com.johndev.notebook.core.local.FolderDataSource
import com.johndev.notebook.core.local.NoteDao
import com.johndev.notebook.core.local.NoteDataSource
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class FolderRepository @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val folderDataSource: FolderDataSource
) {

    fun findNotesByFolder(folder: String, onSuccess: (Flow<List<NoteEntity>>) -> Unit) =
        noteDataSource.findNotesByFolder(folder = folder, callback = {
            onSuccess(it)
        })

    fun getFolderByName(name: String, onSuccess: (FolderEntity) -> Unit) =
        folderDataSource.getByName(name = name, callback = {
            it?.let { onSuccess(it) }
        })

    fun updateFolder(folderEntity: FolderEntity) = folderDataSource.update(folderEntity)

    fun deleteFolder(folderEntity: FolderEntity) = folderDataSource.delete(folderEntity)

}