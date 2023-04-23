package com.johndev.notebook.ui.foldersModule

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.R
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.ui.FolderEntitysModule.FolderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FolderViewModel() : ViewModel() {

    private val repository = FolderRepository()
    
    fun getAll(): Flow<List<FolderEntity>> = repository.getAll()
    
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
                val response = repository.getByName(it.name)
                if (response == null) {
                    repository.insert(it)
                    Log.i("FOLDER_CREATE", "Creando el Folder: ${it.name}")
                }
            }
        }
    }
    
    fun insert(folderEntity: FolderEntity) = viewModelScope.launch(Dispatchers.IO) { repository.insert(folderEntity) }
    
    fun delete(folderEntity: FolderEntity) = viewModelScope.launch(Dispatchers.IO) { repository.delete(folderEntity) }
    
    fun update(folderEntity: FolderEntity) = viewModelScope.launch(Dispatchers.IO) { repository.update(folderEntity) }

}