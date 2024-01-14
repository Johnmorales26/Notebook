package com.johndev.notebook

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.preference.PreferenceManager
import com.johndev.notebook.navigation.Navigation
import com.johndev.notebook.ui.folderModule.viewModel.FolderViewModel
import com.johndev.notebook.ui.homeModule.viewModel.HomeViewModel
import com.johndev.notebook.ui.createNoteModule.viewModel.CreateViewModel
import com.johndev.notebook.ui.editNoteModule.viewModel.EditNotesViewModel
import com.johndev.notebook.ui.searchModule.viewModel.SearchViewModel
import com.johndev.notebook.ui.theme.NotebookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setContent {
            NotebookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation(preferences = sharedPreferences)
                }
            }
        }
    }

}