package com.johndev.notebook.ui.searchModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.ui.searchModule.model.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        viewModelScope.launch(Dispatchers.Main) {
            searchRepository.findNotesByName(title = search, onSuccess = {
                    _noteSearch.value = it
            })
        }
    }

}