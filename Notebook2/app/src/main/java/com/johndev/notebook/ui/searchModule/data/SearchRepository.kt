package com.johndev.notebook.ui.searchModule.data

import com.johndev.notebook.NotesApplication
import com.johndev.notebook.core.local.NoteDao
import javax.inject.Inject

class SearchRepository @Inject constructor() {

    private val dao: NoteDao by lazy { NotesApplication.database.noteDao() }

    fun findNotesByName(title: String) = dao.findNotesByName(title)

}