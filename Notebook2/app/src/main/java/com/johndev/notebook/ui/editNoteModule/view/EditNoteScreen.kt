package com.johndev.notebook.ui.editNoteModule.view

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.johndev.notebook.R
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.navigation.Routes
import com.johndev.notebook.ui.components.FabAddNote
import com.johndev.notebook.ui.components.OutlinedTextFielCustom
import com.johndev.notebook.ui.editNoteModule.viewModel.EditNotesViewModel
import com.johndev.notebook.ui.screens.FormData
import com.johndev.notebook.utils.UtilsNotebook

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditNoteScreen(
    navigationController: NavHostController,
    idNote: Int,
    editNotesViewModel: EditNotesViewModel
) {
    editNotesViewModel.onSearchNote(idNote)
    Scaffold(
        topBar = {
            Header(
                navigationController = navigationController,
                idNote = idNote,
                editNotesViewModel = editNotesViewModel
            )
        },
        content = {
            Body(editNotesViewModel = editNotesViewModel)
        },
        floatingActionButton = {
            Footer(
                navigationController = navigationController,
                editNotesViewModel = editNotesViewModel
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Footer(navigationController: NavHostController, editNotesViewModel: EditNotesViewModel) {
    val isSaveEnable: Boolean by editNotesViewModel.isSaveEnable.observeAsState(initial = false)
    val context = LocalContext.current
    FabAddNote(
        textRes = R.string.update_note,
        iconRes = R.drawable.ic_update,
    ) {
        if (isSaveEnable) {
            editNotesViewModel.saveNote()
            navigationController.clearBackStack(Routes.CreateNoteScreen.route)
            navigationController.navigate(Routes.HomeScreen.route)
        } else {
            Toast.makeText(context, "Fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Body(editNotesViewModel: EditNotesViewModel) {
    val titleState: String by editNotesViewModel.title.observeAsState(initial = "")
    val contentState: String by editNotesViewModel.content.observeAsState(initial = "")
    val createdByState: String by editNotesViewModel.createdBy.observeAsState(initial = "")
    val folderState: String by editNotesViewModel.folder.observeAsState(initial = "")
    val foldersList: List<FolderEntity> by editNotesViewModel.allFolders.observeAsState(initial = emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Divider(color = UtilsNotebook.getColorDividerByTheme().copy(alpha = 0.1f))
        OutlinedTextFielCustom(
            text = titleState,
            textRes = stringResource(R.string.label_write_title),
            styleText = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth(),
            entryText = titleState,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        )
        {
            editNotesViewModel.onNoteChanged(it, contentState, createdByState, folderState)
        }
        FormData(
            categoryRes = R.string.created_by,
            indicationRes = R.string.label_write_name_author,
            entryText = createdByState
        ) {
            editNotesViewModel.onNoteChanged(titleState, contentState, it, folderState)
        }
        FormData(
            categoryRes = R.string.title_folder,
            indicationRes = R.string.label_write_name_author,
            isEnabled = false,
            isVisibleMenu = true,
            exposedMenu = foldersList,
            entryText = folderState
        )
        {
            editNotesViewModel.onNoteChanged(titleState, contentState, createdByState, it)
        }
        Divider(color = UtilsNotebook.getColorDividerByTheme().copy(alpha = 0.1f))
        OutlinedTextFielCustom(
            text = contentState,
            textRes = stringResource(R.string.label_write_content),
            styleText = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth(),
            entryText = contentState,
            isSingleLine = false,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        {
            editNotesViewModel.onNoteChanged(titleState, it, createdByState, folderState)
        }
    }
}

@Composable
fun Header(
    navigationController: NavHostController,
    idNote: Int,
    editNotesViewModel: EditNotesViewModel,
) {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier,
        backgroundColor = UtilsNotebook.getColorByTheme(),
        title = { Text(text = stringResource(id = R.string.title_edit_note)) },
        navigationIcon = {
            IconButton(onClick = {
                navigationController.let {
                    it.navigate(Routes.HomeScreen.route)
                    it.clearBackStack(Routes.EditNoteScreen.route)
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = {
                editNotesViewModel.deleteNote()
                navigationController.clearBackStack(Routes.CreateNoteScreen.route)
                navigationController.navigate(Routes.HomeScreen.route)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null
                )
            }
        }
    )
}