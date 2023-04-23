package com.johndev.notebook.ui.allNotesModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.entities.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {

    private val notesRepository = NotesRepository()

    val allNotes: Flow<List<NoteEntity>> = notesRepository.getAllNotes()

    val allTags = notesRepository.getAllTags

    fun findByName(title: String): Flow<List<NoteEntity>> = notesRepository.findByName(title)

    fun findById(idNote: Int): Flow<NoteEntity?> = notesRepository.findById(idNote)

    fun findByFolder(folder: String): Flow<List<NoteEntity>> = notesRepository.findByFolder(folder)

    fun insert(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.insert(noteEntity)
    }

    fun delete(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.delete(noteEntity)
    }

    fun update(note: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.update(note)
    }

}