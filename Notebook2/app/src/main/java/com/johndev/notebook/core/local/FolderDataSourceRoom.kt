package com.johndev.notebook.core.local

import com.johndev.notebook.entities.FolderEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderDataSourceRoom @Inject constructor(
    private val dao: FolderDao
) : FolderDataSource {

    override fun getAll(callback: (Flow<List<FolderEntity>>) -> Unit) {
        callback(dao.getAll())
    }

    override fun getByName(name: String, callback: (FolderEntity?) -> Unit) {
        callback(dao.getByName(name = name))
    }

    override fun insert(folderEntity: FolderEntity, callback: (Long) -> Unit) {
        callback(dao.insert(folderEntity = folderEntity))
    }

    override fun delete(folderEntity: FolderEntity) {
        dao.delete(folderEntity = folderEntity)
    }

    override fun update(folderEntity: FolderEntity) {
        dao.update(folderEntity = folderEntity)
    }
}