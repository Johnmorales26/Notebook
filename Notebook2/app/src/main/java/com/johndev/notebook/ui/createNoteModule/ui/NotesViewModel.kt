package com.johndev.notebook.ui.createNoteModule.ui

import android.content.Intent
import android.net.Uri
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
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    folderRepository: FolderRepository
) : ViewModel() {

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

    private fun enableOptionSave() = _title.value.orEmpty().isNotEmpty() && _content.value.orEmpty()
        .isNotEmpty() && _createdBy.value.orEmpty().isNotEmpty() && _folder.value.orEmpty()
        .isNotEmpty()

    private fun insertNote(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.insert(noteEntity)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveNote() {
        val note = NoteEntity(
            title = _title.value!!,
            createdBy = _createdBy.value!!,
            lastModified = UtilsNotebook.getLocalDateAndTime(),
            tags = "",
            folder = _folder.value!!,
            content = _content.value!!
        )
        insertNote(note)
    }

    fun onExpandedMenu(isExpanded: Boolean) { _isExpanded.value = isExpanded }

    fun onNoteChanged(title: String, content: String, createdBy: String, folder: String) {
        _title.value = title
        _content.value = content
        _createdBy.value = createdBy
        _folder.value = folder
        _isSaveEnable.value = enableOptionSave()
    }

    fun onRateApp() {
        val appPackageName = "com.johndev.notebook"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("market://details?id=$appPackageName")
            setPackage("com.android.vending")
        }
    }

}