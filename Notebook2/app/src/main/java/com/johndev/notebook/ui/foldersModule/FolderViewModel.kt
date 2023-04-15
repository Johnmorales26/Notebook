package com.johndev.notebook.ui.foldersModule

import androidx.lifecycle.ViewModel
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.ui.FolderEntitysModule.FolderRepository

class FolderViewModel : ViewModel() {

    private val repository = FolderRepository()

    var allFolders: List<FolderEntity> = repository.getAllFolderEntitys()

    fun getFolderByName(nameFolder: String) = repository.getFolderByName(nameFolder)

}