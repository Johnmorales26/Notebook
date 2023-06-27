package com.johndev.notebook.core.local

import com.johndev.notebook.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {

    fun getAll(callback: (Flow<List<NoteEntity>>) -> Unit)

    fun findByName(title: String,callback: (Flow<List<NoteEntity>>) -> Unit)

    fun findNotesByName(title: String, callback: (Flow<List<NoteEntity>>) -> Unit)

    fun findNotesByFolder(folder: String, callback: (Flow<List<NoteEntity>>) -> Unit)

    fun findById(id: Int, callback: (Flow<NoteEntity?>) -> Unit)

    fun insert(noteEntity: NoteEntity, callback: (Long) -> Unit)

    fun delete(noteEntity: NoteEntity)

    fun update(noteEntity: NoteEntity)

}