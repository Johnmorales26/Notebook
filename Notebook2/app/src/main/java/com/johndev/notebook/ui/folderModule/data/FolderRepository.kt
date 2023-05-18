package com.johndev.notebook.ui.folderModule.data

import com.johndev.notebook.NotesApplication
import com.johndev.notebook.core.local.FolderDao
import com.johndev.notebook.core.local.NoteDao
import com.johndev.notebook.entities.FolderEntity
import javax.inject.Inject

class FolderRepository @Inject constructor() {

    private val noteDao: NoteDao by lazy { NotesApplication.database.noteDao() }
    private val folderDao: FolderDao by lazy { NotesApplication.database.folderDao() }

    fun findNotesByFolder(folder: String) = noteDao.findNotesByFolder(folder)

    fun getFolderByName(name: String) = folderDao.getByName(name)

    fun updateFolder(folderEntity: FolderEntity) = folderDao.update(folderEntity)

    fun deleteFolder(folderEntity: FolderEntity) = folderDao.delete(folderEntity)

}