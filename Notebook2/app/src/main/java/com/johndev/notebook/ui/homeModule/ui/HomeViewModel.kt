package com.johndev.notebook.ui.homeModule.ui

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.R
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.ui.homeModule.data.FolderRepository
import com.johndev.notebook.ui.createNoteModule.data.NotesRepository
import com.johndev.notebook.utils.UtilsNotebook.getVersionApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val folderRepository: FolderRepository,
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _folderName = MutableLiveData<String>()
    val folderName: LiveData<String> = _folderName

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    val allFolders: Flow<List<FolderEntity>> = folderRepository.getAll()

    val allNotes: Flow<List<NoteEntity>> = notesRepository.getAllNotes()

    private val _stateTabs = MutableLiveData<Int>()
    val stateTabs: LiveData<Int> = _stateTabs

    private val _openDialog = MutableLiveData<Boolean>()
    val openDialog: LiveData<Boolean> = _openDialog

    fun onOpenDialog(openDialog: Boolean) {
        _openDialog.value = openDialog
    }

    fun onChangeStateTabs(state: Int) {
        _stateTabs.value = state
    }

    fun onNameFolderChange(nameFolder: String) {
        _folderName.value = nameFolder
        _isError.value = folderName.value.isNullOrEmpty() == true
    }

    fun checkFolders(context: Context) {
        val folderCreate = mutableListOf(
            FolderEntity(1, context.getString(R.string.default_folder_work), 0),
            FolderEntity(2, context.getString(R.string.default_folder_personal), 0),
            FolderEntity(3, context.getString(R.string.default_folder_study), 0),
            FolderEntity(4, context.getString(R.string.default_folder_projects), 0),
            FolderEntity(5, context.getString(R.string.default_folder_recipes), 0),
            FolderEntity(6, context.getString(R.string.default_folder_quick_notes), 0)
        )
        folderCreate.forEach {
            viewModelScope.launch(Dispatchers.IO) {
                val response = folderRepository.getByName(it.name)
                if (response == null) {
                    folderRepository.insert(it)
                    Log.i("FOLDER_CREATE", "Creando el Folder: ${it.name}")
                }
            }
        }
    }

    fun delete(folderEntity: FolderEntity) =
        viewModelScope.launch(Dispatchers.IO) { folderRepository.delete(folderEntity) }

    fun update(folderEntity: FolderEntity) =
        viewModelScope.launch(Dispatchers.IO) { folderRepository.update(folderEntity) }

    @OptIn(ExperimentalMaterialApi::class)
    private val _isHidden = MutableLiveData<ModalBottomSheetValue>()

    @OptIn(ExperimentalMaterialApi::class)
    val isHidden: LiveData<ModalBottomSheetValue> = _isHidden

    fun saveFolder() {
        viewModelScope.launch(Dispatchers.IO) {
            folderRepository.insert(
                FolderEntity(
                    name = folderName.value!!,
                    icon = 0
                )
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun getIsHidden(sharedPreferences: SharedPreferences, context: Context) {
        if (sharedPreferences.getInt(getVersionApp(context), 1) == 1) {
            _isHidden.value = ModalBottomSheetValue.Expanded
        } else {
            _isHidden.value = ModalBottomSheetValue.Hidden
        }
    }

    fun saveIsHidden(sharedPreferences: SharedPreferences, isHidden: Int = 1, context: Context) {
        sharedPreferences.edit {
            putInt(getVersionApp(context), isHidden)
            apply()
        }
    }

}