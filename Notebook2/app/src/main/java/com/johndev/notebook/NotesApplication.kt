package com.johndev.notebook

import android.app.Application
import androidx.room.Room
import com.johndev.notebook.core.local.NoteDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NotesApplication : Application()