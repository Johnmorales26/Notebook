package com.johndev.notebook

import android.app.Application
import androidx.room.Room
import com.johndev.notebook.core.local.NoteDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
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