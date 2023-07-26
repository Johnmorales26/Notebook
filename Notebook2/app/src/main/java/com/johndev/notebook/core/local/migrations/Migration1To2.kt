package com.johndev.notebook.core.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1To2 : Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE NoteEntity ADD COLUMN date VARCHAR NOT NULL DEFAULT 'Sin fecha'")

    }

}