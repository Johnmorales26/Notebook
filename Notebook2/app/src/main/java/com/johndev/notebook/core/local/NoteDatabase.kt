package com.johndev.notebook.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johndev.notebook.core.local.migrations.Migration1To2
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity

@Database(
    entities = [
        NoteEntity::class,
        FolderEntity::class],
    version = 2,
    exportSchema = true
)
abstract class NoteDatabase : RoomDatabase() {

    companion object {
        val MIGRATION_1_TO_2 = Migration1To2()
    }

    abstract fun noteDao(): NoteDao
    abstract fun folderDao(): FolderDao

}