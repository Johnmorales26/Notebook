package com.johndev.notebook.navigation

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.johndev.notebook.ui.Onboarding.OnboargingScreen
import com.johndev.notebook.ui.createNoteModule.view.CreateScreen
import com.johndev.notebook.ui.createNoteModule.viewModel.CreateViewModel
import com.johndev.notebook.ui.editNoteModule.view.EditNoteScreen
import com.johndev.notebook.ui.editNoteModule.viewModel.EditNotesViewModel
import com.johndev.notebook.ui.folderModule.view.FolderScreen
import com.johndev.notebook.ui.folderModule.viewModel.FolderViewModel
import com.johndev.notebook.ui.homeModule.view.UpdateHomeModalBottomSheet
import com.johndev.notebook.ui.homeModule.viewModel.HomeViewModel
import com.johndev.notebook.ui.searchModule.view.SearchScreen
import com.johndev.notebook.ui.searchModule.viewModel.SearchViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    preferences: SharedPreferences
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination(preferences),
    ) {
        composable(Routes.OnboardingScreen.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            OnboargingScreen(
                navigationController = navController,
                sharedPreferences = preferences,
                homeViewModel = homeViewModel
            )
        }
        composable(Routes.HomeScreen.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val notesViewModel = hiltViewModel<CreateViewModel>()
            UpdateHomeModalBottomSheet(
                navigationController = navController,
                sharedPreferences = preferences,
                homeViewModel = homeViewModel,
                notesViewModel = notesViewModel
            )
        }
        composable(Routes.CreateNoteScreen.route) {
            val notesViewModel = hiltViewModel<CreateViewModel>()
            CreateScreen(
                navigationController = navController,
                notesViewModel = notesViewModel
            )
        }
        composable(
            Routes.FilterScreen.route,
            arguments = listOf(navArgument("filter") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val notesViewModel = hiltViewModel<CreateViewModel>()
            val folderViewModel = hiltViewModel<FolderViewModel>()
            FolderScreen(
                navigationController = navController,
                folder = backStackEntry.arguments?.getString("filter") ?: "",
                notesViewModel = notesViewModel,
                folderViewModel = folderViewModel
            )
        }
        composable(
            Routes.EditNoteScreen.route,
            arguments = listOf(navArgument("idNote") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val editNotesViewModel = hiltViewModel<EditNotesViewModel>()
            EditNoteScreen(
                navigationController = navController,
                idNote = backStackEntry.arguments?.getInt("idNote") ?: 0,
                editNotesViewModel = editNotesViewModel
            )
        }
        composable(Routes.SearchScreen.route) {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(
                searchViewModel = searchViewModel,
                navigationController = navController
            )
        }
    }
}

private fun startDestination(preferences: SharedPreferences): String {
    return if (preferences.getBoolean("isFirstStart", true)) {
        Routes.OnboardingScreen.route
    } else {
        Routes.HomeScreen.route
    }
}