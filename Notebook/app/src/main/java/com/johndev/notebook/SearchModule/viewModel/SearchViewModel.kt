package com.johndev.notebook.SearchModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.R
import com.johndev.notebook.SearchModule.model.SearchRepository
import com.johndev.notebook.common.entities.NotesEntity
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = SearchRepository()

    private val result = MutableLiveData<List<NotesEntity>?>()
    fun getResult() = result
    private val snackbarMsg = MutableLiveData<Int>()
    fun getSnackbarMsg() = snackbarMsg

    suspend fun searchNote(title: String) {
        viewModelScope.launch {
            try {
                val resultSave = repository.searchNoteByTitle(title)
                result.value = resultSave
                snackbarMsg.value = R.string.main_search_success
            } catch (e: Exception) {
                snackbarMsg.value = R.string.main_no_search_success
            }
        }
    }

    private val snackbarMsgDelete = MutableLiveData<Int>()
    fun getSnackbarMsgDelete() = snackbarMsgDelete

    suspend fun deleteNote(notesEntity: NotesEntity) {
        viewModelScope.launch {
            try {
                repository.deleteNote(notesEntity)
                snackbarMsg.value = R.string.main_delete_success
            } catch (e: Exception) {
                snackbarMsg.value = R.string.delete_error_note
            }
        }
    }

}