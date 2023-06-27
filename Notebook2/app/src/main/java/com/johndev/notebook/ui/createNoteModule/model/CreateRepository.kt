package com.johndev.notebook.ui.createNoteModule.model

import com.johndev.notebook.core.local.FolderDataSource
import com.johndev.notebook.core.local.NoteDataSource
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateRepository @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val folderDataSource: FolderDataSource
) {

    fun getAllFolders(onSuccess: (Flow<List<FolderEntity>>) -> Unit) =
        folderDataSource.getAll { onSuccess(it) }

    fun insert(note: NoteEntity, onSuccess: (Long) -> Unit) =
        noteDataSource.insert(noteEntity = note, callback = { onSuccess(it) })

    fun delete(note: NoteEntity) = noteDataSource.delete(noteEntity = note)

    fun update(note: NoteEntity) = noteDataSource.update(noteEntity = note)

    fun findById(idNote: Int, onSuccess: (Flow<NoteEntity?>) -> Unit) =
        noteDataSource.findById(id = idNote, callback = {
            onSuccess(it)
        })

}