package com.johndev.notebook.ui.editNoteModule.model

import com.johndev.notebook.core.local.FolderDataSource
import com.johndev.notebook.core.local.NoteDataSource
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditRepository @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val folderDataSource: FolderDataSource
) {

    fun getAllFolders(onSuccess: (Flow<List<FolderEntity>>) -> Unit) =
        folderDataSource.getAll { onSuccess(it) }

    fun findById(idNote: Int, onSuccess: (Flow<NoteEntity?>) -> Unit) {
        noteDataSource.findById(id = idNote, callback = {
            onSuccess(it)
        })
    }

    fun delete(noteEntity: NoteEntity) {
        noteDataSource.delete(noteEntity = noteEntity)
    }

    fun update(noteEntity: NoteEntity) {
        noteDataSource.update(noteEntity = noteEntity)
    }

}