package com.johndev.notebook.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "NoteEntity", indices = [Index(value = ["id"], unique = true)])
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var createdBy: String,
    var lastModified: String,
    var tags: String,
    var folder: String,
    var content: String
)
