package com.johndev.notebook.ui.homeModule.data

import com.johndev.notebook.core.local.FolderDao
import com.johndev.notebook.NotesApplication
import com.johndev.notebook.entities.FolderEntity
import javax.inject.Inject

class FolderRepository @Inject constructor() {

    private val dao: FolderDao by lazy { NotesApplication.database.folderDao() }

    fun getAll() = dao.getAll()

    fun getByName(name: String) = dao.getByName(name)

    fun insert(folderEntity: FolderEntity) = dao.insert(folderEntity)

    fun delete(folderEntity: FolderEntity) = dao.delete(folderEntity)

    fun update(folderEntity: FolderEntity) = dao.update(folderEntity)

}