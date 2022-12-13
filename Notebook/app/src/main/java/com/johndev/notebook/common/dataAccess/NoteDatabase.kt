package com.johndev.coupons.common.dataAccess

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johndev.notebook.common.dataAccess.NoteDao
import com.johndev.notebook.common.entities.NotesEntity

/****
 * Project: Coupons
 * From: com.cursosandroidant.coupons
 * Created by Alain Nicolás Tello on 23/02/22 at 19:20
 * All rights reserved 2022.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * Web: www.alainnicolastello.com
 ***/
@Database(entities = [NotesEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase(){
   abstract fun noteDao(): NoteDao
}