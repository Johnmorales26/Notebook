package com.johndev.notebook

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.johndev.notebook.navigation.CreateScreen
import com.johndev.notebook.navigation.DetailsNoteScreen
import com.johndev.notebook.navigation.FilterScreen
import com.johndev.notebook.navigation.HomeScreen
import com.johndev.notebook.navigation.OnboargingScreen
import com.johndev.notebook.navigation.Routes
import com.johndev.notebook.ui.theme.NotebookTheme

class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

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
                        startDestination = startDestination(sharedPreferences)
                    ) {
                        composable(Routes.OnboardingScreen.route) {
                            OnboargingScreen(
                                navigationController,
                                sharedPreferences = sharedPreferences
                            )
                        }
                        composable(Routes.HomeScreen.route) {
                            HomeScreen(
                                navigationController = navigationController
                            )
                        }
                        composable(Routes.CreateScreen.route) {
                            CreateScreen(
                                navigationController = navigationController
                            )
                        }
                        composable(
                            Routes.FilterScreen.route,
                            arguments = listOf(navArgument("filter") {
                                type = NavType.StringType
                            })
                        ) { backStaclEntry ->
                            FilterScreen(
                                navigationController = navigationController,
                                backStaclEntry.arguments?.getString("filter") ?: ""
                            )
                        }
                        composable(
                            Routes.DetailsNoteScreen.route,
                            arguments = listOf(navArgument("idNote") {
                                type = NavType.IntType
                            })
                        ) { backStaclEntry ->
                            DetailsNoteScreen(
                                navigationController,
                                backStaclEntry.arguments?.getInt("idNote") ?: 0
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