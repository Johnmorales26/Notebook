package com.johndev.notebook.common.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "NoteEntity", indices = [Index(value = ["id"], unique = true)])
data class NotesEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var title: String = "",
    val body: String = ""
)
