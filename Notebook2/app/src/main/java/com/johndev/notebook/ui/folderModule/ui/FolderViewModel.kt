package com.johndev.notebook.ui.folderModule.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.ui.folderModule.data.FolderRepository
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

    private fun findFolderByName(folder: String): FolderEntity? = folderRepository.getFolderByName(folder)

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
                ?.apply {
                    name = nameFolder
                    updateFolder(this)
                }
        }
    }

    fun onDeleteNote(folder: String) {
        viewModelScope.launch(Dispatchers.IO) {
            findNotesByFolder(folder).collect {
                if (it.isEmpty()) {
                    findFolderByName(folder)?.let { folder -> deleteFolder(folder) }
                }
            }
        }
    }

    fun findNotesByFolder(folder: String): Flow<List<NoteEntity>> =
        folderRepository.findNotesByFolder(folder)

}