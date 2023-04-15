package com.johndev.notebook.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "FolderEntity", indices = [Index(value = ["id"], unique = true)])
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val icon: Int
)
