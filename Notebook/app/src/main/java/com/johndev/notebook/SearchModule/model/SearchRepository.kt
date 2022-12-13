package com.johndev.notebook.SearchModule.model

import com.johndev.notebook.common.entities.NotesEntity

class SearchRepository {

    private val roomDatabase = RoomSearchDatabase()

    suspend fun searchNoteByTitle(title: String) = roomDatabase.searchNoteByTitle(title)

    suspend fun deleteNote(notesEntity: NotesEntity) = roomDatabase.deleteNote(notesEntity)

}