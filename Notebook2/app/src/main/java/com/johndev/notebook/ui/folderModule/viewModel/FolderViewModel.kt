package com.johndev.notebook.ui.folderModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.ui.folderModule.model.FolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(
    private val folderRepository: FolderRepository
): ViewModel() {

    private val _folderName = MutableLiveData<String>()
    val folderName: LiveData<String> = _folderName

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _openDialog = MutableLiveData<Boolean>()
    val openDialog: LiveData<Boolean> = _openDialog

    private val _isExpanded = MutableLiveData<Boolean>()
    val isExpanded: LiveData<Boolean> = _isExpanded

    private val _folderByName = MutableLiveData<FolderEntity>()

    private val _notesByFolder = MutableLiveData<List<NoteEntity>>()
    val notesByFolder: LiveData<List<NoteEntity>> = _notesByFolder

    private fun findFolderByName(folder: String) {
        folderRepository.getFolderByName(name = folder, onSuccess = {
            _folderByName.value = it
        })
    }

    private fun deleteFolder(folderEntity: FolderEntity) = viewModelScope.launch(Dispatchers.IO) {
        folderRepository.deleteFolder(folderEntity)
    }

    private fun updateFolder(folderEntity: FolderEntity) = viewModelScope.launch(Dispatchers.IO) {
        folderRepository.updateFolder(folderEntity)
    }

    fun onOpenDialog(openDialog: Boolean) { _openDialog.value = openDialog }

    fun onExpandedMenu(isExpanded: Boolean) { _isExpanded.value = isExpanded }

    fun onNameFolderChange(nameFolder: String) {
        _folderName.value = nameFolder
        _isError.value = folderName.value.isNullOrEmpty() == true
    }

    fun onUpdateFolder(folder: String, nameFolder: String) {
        viewModelScope.launch(Dispatchers.IO) {
            findFolderByName(folder)
            _folderByName.value?.apply {
                name = nameFolder
                updateFolder(this)
            }
        }
    }

    fun onDeleteNote(folder: String) {
        viewModelScope.launch(Dispatchers.IO) {
            findNotesByFolder(folder)
                if (_notesByFolder.value?.isEmpty() == true) {
                    findFolderByName(folder)
                        _folderByName.value?.let { folder -> deleteFolder(folder) }
                }
        }
    }

    fun findNotesByFolder(folder: String) {
        folderRepository.findNotesByFolder(folder = folder, onSuccess = {
            viewModelScope.launch(Dispatchers.Main) {
                it.collect {
                    _notesByFolder.value = it
                }
            }
        })
    }

}