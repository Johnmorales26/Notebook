package com.johndev.notebook.editorModule.model

import com.johndev.notebook.common.entities.NotesEntity

class EditorRepository {

    private val roomDatabase = RoomEditorDatabase()

    suspend fun addNote(notesEntity: NotesEntity) = roomDatabase.addNote(notesEntity)

    suspend fun updateNote(notesEntity: NotesEntity) = roomDatabase.updateNote(notesEntity)

    suspend fun consultNoteById(id: Long) = roomDatabase.consultNoteById(id)

}