package com.johndev.notebook.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity

@Database(
    entities = [
        NoteEntity::class,
        FolderEntity::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun folderDao(): FolderDao

}