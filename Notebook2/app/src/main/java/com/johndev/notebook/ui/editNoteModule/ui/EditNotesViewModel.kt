package com.johndev.notebook.ui.editNoteModule.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.ui.createNoteModule.data.NotesRepository
import com.johndev.notebook.ui.homeModule.data.FolderRepository
import com.johndev.notebook.utils.UtilsNotebook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditNotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    folderRepository: FolderRepository
) :
    ViewModel() {

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

    private val _isExpanded = MutableLiveData<Boolean>()
    val isExpanded: LiveData<Boolean> = _isExpanded

    val allFolders: Flow<List<FolderEntity>> = folderRepository.getAll()

    private fun findNoteById(idNote: Int): Flow<NoteEntity?> = notesRepository.findById(idNote)

    private fun enableOptionSave() = _title.value.orEmpty().isNotEmpty() && _content.value.orEmpty()
        .isNotEmpty() && _createdBy.value.orEmpty().isNotEmpty() && _folder.value.orEmpty()
        .isNotEmpty()

    private fun updateNote(note: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.update(note)
    }

    fun deleteNote() = viewModelScope.launch(Dispatchers.IO) {
        _note.value?.let {
            notesRepository.delete(it)
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
            findNoteById(idNote = idNote).collect {
                withContext(Dispatchers.Main) {
                    _note.value = it
                    _title.value = it?.title
                    _content.value = it?.content
                    _createdBy.value = it?.createdBy
                    _folder.value = it?.folder
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