package com.johndev.notebook.core.local

import com.johndev.notebook.entities.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteDataSourceRoom @Inject constructor(
    private val dao: NoteDao
) : NoteDataSource {

    override fun getAll(callback: (Flow<List<NoteEntity>>) -> Unit) {
        callback(dao.getAll())
    }

    override fun findByName(title: String, callback: (Flow<List<NoteEntity>>) -> Unit) {
        callback(dao.findByName(title = title))
    }

    override fun findNotesByName(
        title: String,
        callback: (Flow<List<NoteEntity>>) -> Unit
    ) {
        callback(dao.findByName(title = title))
    }

    override fun findNotesByFolder(folder: String, callback: (Flow<List<NoteEntity>>) -> Unit) {
        callback(dao.findNotesByFolder(folder = folder))
    }

    override fun findById(id: Int, callback: (Flow<NoteEntity?>) -> Unit) {
        callback(dao.findById(id = id))
    }

    override fun insert(noteEntity: NoteEntity, callback: (Long) -> Unit) {
        callback(dao.insert(noteEntity = noteEntity))
    }

    override fun delete(noteEntity: NoteEntity) {
        dao.delete(noteEntity = noteEntity)
    }

    override fun update(noteEntity: NoteEntity) {
        dao.update(noteEntity = noteEntity)
    }
}