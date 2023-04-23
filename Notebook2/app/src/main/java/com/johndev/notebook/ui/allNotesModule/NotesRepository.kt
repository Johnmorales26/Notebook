package com.johndev.notebook.ui.allNotesModule

import com.johndev.notebook.data.NoteDao
import com.johndev.notebook.data.NotesApplication
import com.johndev.notebook.entities.NoteEntity

class NotesRepository {

    private val dao: NoteDao by lazy { NotesApplication.database.noteDao() }

    val getAllTags = listOf(
        "Trabajo", "Personal", "Estudio", "Proyectos", "Tareas pendientes", "Ideas",
        "Inspiración", "Viajes", "Lista de compras", "Recetas", "Salud", "Finanzas",
        "Familia", "Amigos", "Eventos", "Importante", "Prioritario", "Social",
        "Recordatorios", "Notas rápidas"
    )

    fun getAllNotes() = dao.getAll()

    fun insert(note: NoteEntity) = dao.insert(note)

    fun delete(note: NoteEntity) = dao.delete(note)

    fun update(note: NoteEntity) = dao.update(note)

    fun findByName(title: String) = dao.findByName(title)

    fun findById(idNote: Int) = dao.findById(idNote)

    fun findByFolder(folder: String) = dao.findByFolder(folder)

}