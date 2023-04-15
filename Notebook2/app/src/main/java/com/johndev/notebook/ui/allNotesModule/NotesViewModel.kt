package com.johndev.notebook.ui.allNotesModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.entities.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {

    private val repository = NotesRepository()

    var allNotes: Flow<List<NoteEntity>> = repository.getAllNotes()

    var allTags = repository.getAllTags

    fun findByName(title: String): Flow<List<NoteEntity>> = repository.findByName(title)

    fun findById(idNote: Int): Flow<NoteEntity?> = repository.findById(idNote)

    fun findByFolder(folder: String): Flow<List<NoteEntity>> = repository.findByFolder(folder)

    fun insert(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) { repository.insert(noteEntity) }

    fun delete(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) { repository.delete(noteEntity) }

    fun update(note: NoteEntity) {
        viewModelScope.launch {
            repository.update(note)
        }
    }

}