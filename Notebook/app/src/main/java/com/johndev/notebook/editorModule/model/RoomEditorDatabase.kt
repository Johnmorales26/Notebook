package com.johndev.notebook.editorModule.model

import com.johndev.notebook.NotesApplication
import com.johndev.notebook.common.dataAccess.NoteDao
import com.johndev.notebook.common.entities.NotesEntity

class RoomEditorDatabase {

    private val dao: NoteDao by lazy { NotesApplication.database.noteDao() }

    suspend fun updateNote(notesEntity: NotesEntity) = dao.updateNote(notesEntity)

    suspend fun addNote(notesEntity: NotesEntity) = dao.addNote(notesEntity)

    suspend fun consultNoteById(id: Long) = dao.consultNoteById(id)

}