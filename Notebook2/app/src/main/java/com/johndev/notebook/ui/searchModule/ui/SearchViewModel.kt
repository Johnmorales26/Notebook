package com.johndev.notebook.ui.searchModule.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.ui.searchModule.data.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchTitle = MutableLiveData<String>()
    val searchTitle: LiveData<String> = _searchTitle

    private val _noteSearch = MutableLiveData<List<NoteEntity>>()
    val noteSearch: LiveData<List<NoteEntity>> = _noteSearch

    fun onSearchChanged(search: String) {
        _searchTitle.value = search
        viewModelScope.launch(Dispatchers.IO) {
            searchRepository.findNotesByName(search).collect {
                withContext(Dispatchers.Main) {
                    _noteSearch.value = it
                }
            }
        }
    }

}