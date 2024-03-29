package com.johndev.notebook.navigation

sealed class Routes(val route: String) {

    object OnboardingScreen : Routes("OnboardingScreen")

    object HomeScreen : Routes("HomeScreen")

    object FilterScreen : Routes("FilterScreen/{filter}") {
        fun createRoute(filter: String) = "FilterScreen/$filter"
    }

    object CreateNoteScreen : Routes("CreateNoteScreen")
    object EditNoteScreen : Routes("EditNoteScreen/{idNote}") {
        fun createRoute(idNote: Int) = "EditNoteScreen/$idNote"
    }
    object SearchScreen : Routes("SearchScreen")

}