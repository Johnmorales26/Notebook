package com.johndev.notebook.common.dataAccess

import com.johndev.notebook.common.entities.NotesEntity

interface NoteListener {

    fun onNoteListener(notes: NotesEntity)

    fun onNoteLongListener(notes: NotesEntity)

}