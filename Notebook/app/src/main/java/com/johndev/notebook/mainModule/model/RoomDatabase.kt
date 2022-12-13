package com.johndev.notebook.mainModule.model

import com.johndev.notebook.NotesApplication
import com.johndev.notebook.common.dataAccess.NoteDao
import com.johndev.notebook.common.entities.NotesEntity

class RoomDatabase {

    private val dao: NoteDao by lazy { NotesApplication.database.noteDao() }

    suspend fun getAllNotes() = dao.getAllNotes()

    suspend fun deleteNote(notesEntity: NotesEntity) = dao.deleteNote(notesEntity)

}