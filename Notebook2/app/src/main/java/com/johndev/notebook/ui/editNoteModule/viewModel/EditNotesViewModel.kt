package com.johndev.notebook.ui.editNoteModule.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.ui.editNoteModule.model.EditRepository
import com.johndev.notebook.ui.homeModule.model.HomeRepository
import com.johndev.notebook.utils.UtilsNotebook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditNotesViewModel @Inject constructor(
    private val editRepository: EditRepository
) :
    ViewModel() {

    init {
        getAllFolders()
    }

    private val _note = MutableLiveData<NoteEntity>()

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _content = MutableLiveData<String>()
    val content: LiveData<String> = _content

    private val _createdBy = MutableLiveData<String>()
    val createdBy: LiveData<String> = _createdBy

    private val _folder = MutableLiveData<String>()
    val folder: LiveData<String> = _folder

    private val _isSaveEnable = MutableLiveData<Boolean>()
    val isSaveEnable: LiveData<Boolean> = _isSaveEnable

    private val _onSearchNote = MutableLiveData<NoteEntity?>(null)

    private val _allFolders = MutableLiveData<List<FolderEntity>>()
    val allFolders: LiveData<List<FolderEntity>> = _allFolders

    private fun getAllFolders() {
        editRepository.getAllFolders {
            viewModelScope.launch(Dispatchers.IO) {
                it.collect {
                    _allFolders.postValue(it)
                }
            }
        }
    }

    private fun findNoteById(idNote: Int) {
        editRepository.findById(idNote = idNote, onSuccess = {
            viewModelScope.launch {
                it.collect {
                    it?.let {
                        _onSearchNote.value = it
                    }
                }
            }
        })
    }

    private fun enableOptionSave() = _title.value.orEmpty().isNotEmpty() && _content.value.orEmpty()
        .isNotEmpty() && _createdBy.value.orEmpty().isNotEmpty() && _folder.value.orEmpty()
        .isNotEmpty()

    private fun updateNote(note: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        editRepository.update(noteEntity = note)
    }

    fun deleteNote() = viewModelScope.launch(Dispatchers.IO) {
        _note.value?.let {
            editRepository.delete(it)
        }
    }

    fun onNoteChanged(
        title: String,
        contentState: String,
        createdByState: String,
        folderState: String
    ) {
        _title.value = title
        _content.value = contentState
        _createdBy.value = createdByState
        _folder.value = folderState
        _isSaveEnable.value = enableOptionSave()
    }

    fun onSearchNote(idNote: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            findNoteById(idNote = idNote)
            _onSearchNote.value?.let {
                withContext(Dispatchers.Main) {
                    _note.value = it
                    _title.value = it.title
                    _content.value = it.content
                    _createdBy.value = it.createdBy
                    _folder.value = it.folder
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveNote() {
        _note.value?.let {
            it.title = _title.value!!
            it.createdBy = _createdBy.value!!
            it.lastModified = UtilsNotebook.getLocalDateAndTime()
            it.tags = ""
            it.folder = _folder.value!!
            it.content = _content.value!!
            updateNote(it)
        }
    }

}