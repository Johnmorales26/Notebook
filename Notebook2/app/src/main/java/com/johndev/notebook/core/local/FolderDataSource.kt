package com.johndev.notebook.core.local

import com.johndev.notebook.entities.FolderEntity
import kotlinx.coroutines.flow.Flow

interface FolderDataSource {

    fun getAll(callback: (Flow<List<FolderEntity>>) -> Unit)

    fun getByName(name: String, callback: (FolderEntity?) -> Unit)

    fun insert(folderEntity: FolderEntity, callback: (Long) -> Unit)

    fun delete(folderEntity: FolderEntity)

    fun update(folderEntity: FolderEntity)

}