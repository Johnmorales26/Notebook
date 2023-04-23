package com.johndev.notebook.ui.allNotesModule

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.johndev.notebook.R
import com.johndev.notebook.data.ViewModels
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.ui.components.NoDataView
import com.johndev.notebook.ui.components.NotesCards
import com.johndev.notebook.ui.components.TopAppBarNotes

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FilterScreen(navigationController: NavHostController, filter: String) {
    val notesListState: List<NoteEntity> by ViewModels.getInstanceNotesVM().findByFolder(filter)
        .collectAsState(initial = emptyList())
    Scaffold(
        topBar = {
            TopAppBarNotes(
                titleRes = R.string.title_folder,
                navIconRes = R.drawable.ic_arrow_back,
                navigationController = navigationController
            ) {

            }
        },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                if (notesListState.isNotEmpty()) {
                    /*OutlinedTextFielCustom(
                        textRes = "Search by the keyboard",
                        styleText = TextStyle.Default,
                        modifier = Modifier.fillMaxWidth(),
                        onTextChange = {

                        })*/
                    NotesCards(notesListState, navigationController)
                } else {
                    NoDataView(textRes = R.string.message_not_found, imgRes = R.drawable.img_not_found)
                }
            }
        }
    )
}