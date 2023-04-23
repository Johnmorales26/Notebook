package com.johndev.notebook.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.johndev.notebook.R
import com.johndev.notebook.data.Utils
import com.johndev.notebook.data.ViewModels
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.navigation.Routes
import com.johndev.notebook.ui.components.FabAddNote
import com.johndev.notebook.ui.components.OutlinedTextFielCustom
import com.johndev.notebook.ui.components.TopAppBarNotes

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateScreen(navigationController: NavHostController, noteEntity: NoteEntity? = null) {
    navigationController.clearBackStack(Routes.HomeScreen.route)
    var titleState by remember { mutableStateOf(noteEntity?.title ?: "") }
    var contentState by remember { mutableStateOf(noteEntity?.content ?: "") }
    var createdByState by remember { mutableStateOf(noteEntity?.createdBy ?: "") }
    var folderState by remember { mutableStateOf(noteEntity?.folder ?: "") }
    var isVisible by remember { mutableStateOf(false) }
    val foldersList: List<FolderEntity> by ViewModels.getInstanceFoldersVM().getAll().collectAsState(
        initial = emptyList()
    )
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBarNotes(
                titleRes = if (noteEntity == null) R.string.title_create_note else R.string.title_edit_note,
                navIconRes = R.drawable.ic_arrow_back,
                navigationController = navigationController,
                deleteNote = noteEntity != null
            ) {
                ViewModels.getInstanceNotesVM().delete(noteEntity!!)
                navigationController.clearBackStack(Routes.CreateNoteScreen.route)
                navigationController.navigate(Routes.HomeScreen.route)
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Divider(color = Utils.getColorDividerByTheme().copy(alpha = 0.1f))
                OutlinedTextFielCustom(
                    textRes = stringResource(R.string.label_write_title),
                    styleText = MaterialTheme.typography.h6,
                    modifier = Modifier.fillMaxWidth(),
                    entryText = titleState
                )
                {
                    titleState = it
                    isVisible = titleState != "" && contentState != ""
                }
                FormData(
                    categoryRes = R.string.created_by,
                    indicationRes = R.string.label_write_name_author,
                    entryText = createdByState
                ) {
                    createdByState = it
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
                    folderState = it
                }
                Divider(color = Utils.getColorDividerByTheme().copy(alpha = 0.1f))
                OutlinedTextFielCustom(
                    textRes = stringResource(R.string.label_write_content),
                    styleText = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxWidth(),
                    entryText = contentState
                )
                {
                    contentState = it
                    isVisible = titleState != "" && contentState != ""
                }
            }
        },
        floatingActionButton = {
            AnimatedVisibility(true) {
                FabAddNote(
                    textRes = R.string.save_note,
                    iconRes = R.drawable.ic_save
                ) {
                    if (titleState.isNotEmpty() && createdByState.isNotEmpty() && folderState.isNotEmpty() && contentState.isNotEmpty()) {
                        if (noteEntity == null) {
                            //  Create Note
                            val note = NoteEntity(
                                title = titleState,
                                createdBy = createdByState,
                                lastModified = Utils.getLocalDateAndTime(),
                                tags = "",
                                folder = folderState,
                                content = contentState
                            )
                            ViewModels.getInstanceNotesVM().insert(note)
                        } else {
                            // Update Note
                            noteEntity.apply {
                                title = titleState
                                createdBy = createdByState
                                lastModified = Utils.getLocalDateAndTime()
                                tags = ""
                                folder = folderState
                                content = contentState
                            }
                            ViewModels.getInstanceNotesVM().update(noteEntity)
                        }
                        navigationController.clearBackStack(Routes.CreateNoteScreen.route)
                        navigationController.navigate(Routes.HomeScreen.route)
                    } else {
                        Toast.makeText(context, "Fill all the fields", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )
}