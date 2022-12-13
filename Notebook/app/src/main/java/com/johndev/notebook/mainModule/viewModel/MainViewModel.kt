package com.johndev.notebook.mainModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johndev.notebook.R
import com.johndev.notebook.common.entities.NotesEntity
import com.johndev.notebook.mainModule.model.MainRepository
import kotlinx.coroutines.launch

/****
 * Project: Coupons
 * From: com.cursosandroidant.coupons.mainModule.viewModel
 * Created by Alain Nicolás Tello on 24/02/22 at 13:59
 * All rights reserved 2022.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * Web: www.alainnicolastello.com
 ***/
class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    private val result = MutableLiveData<List<NotesEntity>?>()
    fun getResult() = result
    private val loaded = MutableLiveData<Boolean>()
    fun isLoaded() = loaded
    private val snackbarMsg = MutableLiveData<Int>()
    fun getSnackbarMsg() = snackbarMsg

    suspend fun getAllNotes() {
        viewModelScope.launch {
            try {
                loaded.value = false
                val resultServer = repository.getAllNotes()
                result.value = resultServer
            } catch (e: Exception) {
                snackbarMsg.value = R.string.login_error_server
            } finally {
                loaded.value = true
            }
        }
    }

    private val snackbarMsgDelete = MutableLiveData<Int>()
    fun getSnackbarMsgDelete() = snackbarMsgDelete

    suspend fun deleteNote(notesEntity: NotesEntity) {
        viewModelScope.launch {
            try {
                repository.deleteNote(notesEntity)
                snackbarMsg.value = R.string.main_delete_success
            } catch (e: Exception) {
                snackbarMsg.value = R.string.delete_error_note
            }
        }
    }

}