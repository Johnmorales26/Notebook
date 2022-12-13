package com.johndev.notebook.common.dataAccess

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.johndev.notebook.common.entities.NotesEntity

@Dao
interface NoteDao {

   @Query("SELECT * FROM NoteEntity")
   suspend fun getAllNotes(): List<NotesEntity>?

   @Query("SELECT * FROM NoteEntity WHERE id = :id")
   suspend fun consultNoteById(id: Long): NotesEntity?

   @Query("SELECT * FROM NoteEntity WHERE title = :title")
   suspend fun searchNoteByTitle(title: String): List<NotesEntity>?

   @Insert
   suspend fun addNote(notesEntity: NotesEntity): Long

   @Update
   suspend fun updateNote(notesEntity: NotesEntity)

   @Delete
   suspend fun deleteNote(notesEntity: NotesEntity)

}