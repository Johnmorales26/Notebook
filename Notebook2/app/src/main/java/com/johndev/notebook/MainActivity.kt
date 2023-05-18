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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.preference.PreferenceManager
import com.johndev.notebook.navigation.Routes
import com.johndev.notebook.ui.Onboarding.OnboargingScreen
import com.johndev.notebook.ui.folderModule.ui.FolderScreen
import com.johndev.notebook.ui.folderModule.ui.FolderViewModel
import com.johndev.notebook.ui.homeModule.ui.HomeViewModel
import com.johndev.notebook.ui.createNoteModule.ui.CreateScreen
import com.johndev.notebook.ui.homeModule.ui.UpdateHomeModalBottomSheet
import com.johndev.notebook.ui.createNoteModule.ui.NotesViewModel
import com.johndev.notebook.ui.editNoteModule.ui.EditNoteScreen
import com.johndev.notebook.ui.editNoteModule.ui.EditNotesViewModel
import com.johndev.notebook.ui.searchModule.ui.SearchScreen
import com.johndev.notebook.ui.searchModule.ui.SearchViewModel
import com.johndev.notebook.ui.theme.NotebookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val homeViewModel: HomeViewModel by viewModels()
    private val notesViewModel: NotesViewModel by viewModels()
    private val folderViewModel: FolderViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val editNotesViewModel: EditNotesViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setContent {
            NotebookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = startDestination(sharedPreferences),
                    ) {
                        composable(Routes.OnboardingScreen.route) {
                            OnboargingScreen(
                                navigationController,
                                sharedPreferences = sharedPreferences,
                                homeViewModel = homeViewModel
                            )
                        }
                        composable(Routes.HomeScreen.route) {
                            UpdateHomeModalBottomSheet(
                                navigationController = navigationController,
                                sharedPreferences = sharedPreferences,
                                homeViewModel = homeViewModel,
                                notesViewModel = notesViewModel
                            )
                        }
                        composable(Routes.CreateNoteScreen.route) {
                            CreateScreen(
                                navigationController = navigationController,
                                notesViewModel = notesViewModel
                            )
                        }
                        composable(
                            Routes.FilterScreen.route,
                            arguments = listOf(navArgument("filter") {
                                type = NavType.StringType
                            })
                        ) { backStaclEntry ->
                            FolderScreen(
                                navigationController = navigationController,
                                folder = backStaclEntry.arguments?.getString("filter") ?: "",
                                notesViewModel = notesViewModel,
                                folderViewModel = folderViewModel
                            )
                        }
                        composable(
                            Routes.EditNoteScreen.route,
                            arguments = listOf(navArgument("idNote") {
                                type = NavType.IntType
                            })
                        ) { backStaclEntry ->
                            EditNoteScreen(
                                navigationController = navigationController,
                                idNote = backStaclEntry.arguments?.getInt("idNote") ?: 0,
                                editNotesViewModel = editNotesViewModel
                            )
                        }
                        composable(Routes.SearchScreen.route) {
                            SearchScreen(
                                searchViewModel = searchViewModel,
                                navigationController = navigationController
                            )
                        }
                    }
                }
            }
        }
    }

    private fun startDestination(sharedPreferences: SharedPreferences): String {
        return if (sharedPreferences.getBoolean("isFirstStart", true)) {
            Routes.OnboardingScreen.route
        } else {
            Routes.HomeScreen.route
        }
    }

}