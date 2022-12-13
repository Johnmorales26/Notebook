package com.johndev.notebook

import android.app.Application
import androidx.room.Room
import com.johndev.coupons.common.dataAccess.NoteDatabase

/****
 * Project: Coupons
 * From: com.cursosandroidant.coupons
 * Created by Alain Nicolás Tello on 23/02/22 at 19:22
 * All rights reserved 2022.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * Web: www.alainnicolastello.com
 ***/
class NotesApplication : Application(){

   companion object{
      lateinit var database: NoteDatabase
   }

   override fun onCreate() {
      super.onCreate()

      database = Room.databaseBuilder(this, NoteDatabase::class.java, "NoteDatabase").build()
   }
}