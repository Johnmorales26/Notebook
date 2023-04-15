package com.johndev.notebook.navigation

sealed class Routes(val route: String) {

    object OnboardingScreen : Routes("OnboardingScreen")

    object HomeScreen : Routes("HomeScreen")

    object FilterScreen : Routes("FilterScreen/{filter}") {
        fun createRoute(filter: String) = "FilterScreen/$filter"
    }

    object CreateScreen : Routes("CreateScreen")

    object DetailsNoteScreen : Routes("DetailsNoteScreen/{idNote}") {
        fun createRoute(idNote: Int) = "DetailsNoteScreen/$idNote"
    }

}