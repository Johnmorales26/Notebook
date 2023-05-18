package com.johndev.notebook.core.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.johndev.notebook.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteEntity")
    fun getAll(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE title = :title")
    fun findByName(title: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE title LIKE :title")
    fun findNotesByName(title: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE folder = :folder")
    fun findNotesByFolder(folder: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id = :id")
    fun findById(id: Int): Flow<NoteEntity?>

    @Insert
    fun insert(noteEntity: NoteEntity)

    @Delete
    fun delete(noteEntity: NoteEntity)

    @Update
    fun update(noteEntity: NoteEntity)

}