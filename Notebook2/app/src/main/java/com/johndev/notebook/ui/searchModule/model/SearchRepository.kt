package com.johndev.notebook.ui.searchModule.model

import com.johndev.notebook.core.local.NoteDataSource
import com.johndev.notebook.entities.NoteEntity
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val noteDataSource: NoteDataSource
) {

    fun findNotesByName(title: String, onSuccess: (List<NoteEntity>) -> Unit) =
        noteDataSource.findNotesByName(title = title, callback = {
            runBlocking {
                it.collect {
                    onSuccess(it)
                }
            }
        })

}