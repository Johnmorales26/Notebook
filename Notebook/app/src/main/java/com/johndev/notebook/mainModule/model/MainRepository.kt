package com.johndev.notebook.mainModule.model

import com.johndev.notebook.common.entities.NotesEntity

class MainRepository {

    private val roomDatabase = RoomDatabase()

    suspend fun getAllNotes() = roomDatabase.getAllNotes()

    suspend fun deleteNote(notesEntity: NotesEntity) = roomDatabase.deleteNote(notesEntity)

}