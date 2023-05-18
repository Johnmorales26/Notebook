package com.johndev.notebook.ui.createNoteModule.data

import com.johndev.notebook.core.local.NoteDao
import com.johndev.notebook.NotesApplication
import com.johndev.notebook.entities.NoteEntity
import javax.inject.Inject

class NotesRepository @Inject constructor() {

    private val dao: NoteDao by lazy { NotesApplication.database.noteDao() }

    fun getAllNotes() = dao.getAll()

    fun insert(note: NoteEntity) = dao.insert(note)

    fun delete(note: NoteEntity) = dao.delete(note)

    fun update(note: NoteEntity) = dao.update(note)

    fun findById(idNote: Int) = dao.findById(idNote)

}