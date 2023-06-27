package com.johndev.notebook.ui.createNoteModule.view

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.navigation.Routes
import com.johndev.notebook.ui.components.FabAddNote
import com.johndev.notebook.ui.components.OutlinedTextFielCustom
import com.johndev.notebook.ui.createNoteModule.viewModel.CreateViewModel
import com.johndev.notebook.ui.screens.FormData
import com.johndev.notebook.utils.UtilsNotebook

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateScreen(
    navigationController: NavHostController,
    notesViewModel: CreateViewModel
) {
    navigationController.clearBackStack(Routes.HomeScreen.route)
    Scaffold(
        topBar = {
            Header(
                navigationController = navigationController,
                notesViewModel = notesViewModel,
            )
        },
        content = {
            Body(
                notesViewModel = notesViewModel,
            )
        },
        floatingActionButton = {
            Footer(
                notesViewModel = notesViewModel,
                navigationController = navigationController
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Footer(
    notesViewModel: CreateViewModel,
    navigationController: NavHostController,
) {
    val msg: Int? by notesViewModel.msg.observeAsState(initial = null)
    val isSaveEnable: Boolean by notesViewModel.isSaveEnable.observeAsState(initial = false)
    val message = stringResource(R.string.message_fill_all_the_fields)
    val context = LocalContext.current
    FabAddNote(
        textRes = R.string.save_note,
        iconRes = R.drawable.ic_save,
    ) {
        if (isSaveEnable) {
            notesViewModel.saveNote()
            msg?.let {
                Toast.makeText(context, context.getString(msg!!), Toast.LENGTH_SHORT).show()
                navigationController.clearBackStack(Routes.CreateNoteScreen.route)
                navigationController.navigate(Routes.HomeScreen.route)
            }
        } else {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Body(notesViewModel: CreateViewModel) {
    val titleState: String by notesViewModel.title.observeAsState(initial = "")
    val contentState: String by notesViewModel.content.observeAsState(initial = "")
    val createdByState: String by notesViewModel.createdBy.observeAsState(initial = "")
    val folderState: String by notesViewModel.folder.observeAsState(initial = "")
    val foldersList: List<FolderEntity> by notesViewModel.allFolders.observeAsState(initial = emptyList())
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
            notesViewModel.onNoteChanged(it, contentState, createdByState, folderState)
        }
        FormData(
            categoryRes = R.string.created_by,
            indicationRes = R.string.label_write_name_author,
            entryText = createdByState
        ) {
            notesViewModel.onNoteChanged(titleState, contentState, it, folderState)
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
            notesViewModel.onNoteChanged(titleState, contentState, createdByState, it)
        }
        Divider(color = UtilsNotebook.getColorDividerByTheme().copy(alpha = 0.1f))
        OutlinedTextFielCustom(
            text = contentState,
            textRes = stringResource(R.string.label_write_content),
            styleText = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            entryText = contentState,
            isSingleLine = false,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        {
            notesViewModel.onNoteChanged(titleState, it, createdByState, folderState)
        }
    }
}

@Composable
fun Header(
    navigationController: NavHostController,
    notesViewModel: CreateViewModel,
) {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier,
        backgroundColor = UtilsNotebook.getColorByTheme(),
        title = { Text(text = stringResource(id = R.string.title_create_note)) },
        navigationIcon = {
            IconButton(onClick = {
                navigationController.let {
                    it.navigate(Routes.HomeScreen.route)
                    it.clearBackStack(Routes.CreateNoteScreen.route)
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        }
    )
}