package com.johndev.notebook.ui.FolderEntitysModule

import com.johndev.notebook.data.FolderDao
import com.johndev.notebook.data.NotesApplication
import com.johndev.notebook.entities.FolderEntity

class FolderRepository {

    private val dao: FolderDao by lazy { NotesApplication.database.folderDao() }

    fun getAll() = dao.getAll()

    fun getByName(name: String) = dao.getByName(name)

    fun insert(folderEntity: FolderEntity) = dao.insert(folderEntity)

    fun delete(folderEntity: FolderEntity) = dao.delete(folderEntity)

    fun update(folderEntity: FolderEntity) = dao.update(folderEntity)

}