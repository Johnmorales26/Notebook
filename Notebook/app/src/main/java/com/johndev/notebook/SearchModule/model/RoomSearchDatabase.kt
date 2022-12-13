package com.johndev.notebook.SearchModule.model

import com.johndev.notebook.NotesApplication
import com.johndev.notebook.common.dataAccess.NoteDao
import com.johndev.notebook.common.entities.NotesEntity

class RoomSearchDatabase {

    private val dao: NoteDao by lazy { NotesApplication.database.noteDao() }

    suspend fun searchNoteByTitle(title: String) = dao.searchNoteByTitle(title)

    suspend fun deleteNote(notesEntity: NotesEntity) = dao.deleteNote(notesEntity)

}