package com.johndev.notebook.data

import android.app.Application
import androidx.room.Room

class NotesApplication : Application() {

    companion object {
        lateinit var database: NoteDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room
            .databaseBuilder(this, NoteDatabase::class.java, "NoteDatabase")
            .build()
    }

}