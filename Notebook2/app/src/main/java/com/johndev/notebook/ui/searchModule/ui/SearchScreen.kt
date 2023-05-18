package com.johndev.notebook.ui.searchModule.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.johndev.notebook.R
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.navigation.Routes
import com.johndev.notebook.ui.components.CardNote
import com.johndev.notebook.ui.components.NotesCards
import com.johndev.notebook.ui.components.OutlinedTextFielCustom
import com.johndev.notebook.utils.UtilsNotebook

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(searchViewModel: SearchViewModel, navigationController: NavHostController) {
    Scaffold(
        topBar = { Header(navigationController = navigationController) },
        content = { Body(searchViewModel = searchViewModel) }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Body(searchViewModel: SearchViewModel) {
    val title: String by searchViewModel.searchTitle.observeAsState(initial = "")
    val notesList: List<NoteEntity> by searchViewModel.noteSearch.observeAsState(initial = emptyList())
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextFielCustom(
            text = title,
            textRes = stringResource(R.string.label_write_title),
            styleText = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth(),
            entryText = title,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        ) {
            searchViewModel.onSearchChanged(it)
        }
        LazyColumn {
            items(notesList.size) {
                CardNote(notesList[it]) {

                }
            }
        }
    }
}

@Composable
fun Header(navigationController: NavHostController) {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier,
        backgroundColor = UtilsNotebook.getColorByTheme(),
        title = { Text(text = "Search Notes") },
        navigationIcon = {
            IconButton(onClick = {
                navigationController.navigate(Routes.HomeScreen.route)
                navigationController.clearBackStack(Routes.SearchScreen.route)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        },
    )
}
