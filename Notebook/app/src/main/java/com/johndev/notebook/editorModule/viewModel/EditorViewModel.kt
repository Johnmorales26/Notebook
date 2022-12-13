package com.johndev.notebook.editorModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.R
import com.johndev.notebook.common.entities.NotesEntity
import com.johndev.notebook.editorModule.model.EditorRepository
import com.johndev.notebook.mainModule.model.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditorViewModel : ViewModel() {
    private val repository = EditorRepository()

    private val result = MutableLiveData<Long>()
    fun getResult() = result
    private val snackbarMsg = MutableLiveData<Int>()
    fun getSnackbarMsg() = snackbarMsg

    suspend fun addNote(notesEntity: NotesEntity) {
        viewModelScope.launch {
            try {
                val resultSave = repository.addNote(notesEntity)
                result.value = resultSave
                snackbarMsg.value = R.string.main_save_success
            } catch (e: Exception) {
                snackbarMsg.value = R.string.save_error_note
            }
        }
    }

    private val resultSearch = MutableLiveData<NotesEntity?>()
    fun getResultSearch() = resultSearch
    private val snackbarMsgSearch = MutableLiveData<Int>()
    fun getSnackbarMsgSearch() = snackbarMsgSearch

    suspend fun consultNoteById(id: Long) {
        viewModelScope.launch {
            try {
                val resultSave = repository.consultNoteById(id)
                resultSearch.value = resultSave
                snackbarMsg.value = R.string.main_search_success
            } catch (e: Exception) {
                snackbarMsg.value = R.string.save_error_note
            }
        }
    }

    private val snackbarMsgUpdate = MutableLiveData<Int>()
    fun getSnackbarMsgUpdate() = snackbarMsgUpdate

    suspend fun updateNote(notesEntity: NotesEntity) {
        viewModelScope.launch {
            try {
                repository.updateNote(notesEntity)
                snackbarMsg.value = R.string.main_update_success
            } catch (e: Exception) {
                snackbarMsg.value = R.string.save_error_note
            }
        }
    }

}