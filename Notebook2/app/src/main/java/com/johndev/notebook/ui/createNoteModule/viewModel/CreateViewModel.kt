package com.johndev.notebook.ui.createNoteModule.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.R
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.ui.createNoteModule.model.CreateRepository
import com.johndev.notebook.utils.Constans.PACKAGE_NAME
import com.johndev.notebook.utils.UtilsNotebook
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val notesRepository: CreateRepository
) : ViewModel() {

    init {
        getAllFolders()
    }

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

    private val _allFolders = MutableLiveData<List<FolderEntity>>()
    val allFolders: LiveData<List<FolderEntity>> = _allFolders

    private val _msg = MutableLiveData<Int?>()
    val msg: LiveData<Int?> = _msg

    private fun getAllFolders() {
            notesRepository.getAllFolders {
                viewModelScope.launch(Dispatchers.IO) {
                it.collect {
                    _allFolders.postValue(it)
                }
            }
        }
    }

    private fun enableOptionSave() = _title.value.orEmpty().isNotEmpty() && _content.value.orEmpty()
        .isNotEmpty() && _createdBy.value.orEmpty().isNotEmpty() && _folder.value.orEmpty()
        .isNotEmpty()

    private fun insertNote(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.insert(note = noteEntity) {
            if (it < 1) {
                _msg.postValue(R.string.create_note_error)
            } else {
                _msg.postValue(R.string.create_note_success)
            }
        }
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
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("market://details?id=$PACKAGE_NAME")
            setPackage("com.android.vending")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

}