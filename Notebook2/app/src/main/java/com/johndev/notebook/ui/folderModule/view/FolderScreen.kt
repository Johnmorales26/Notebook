package com.johndev.notebook.ui.folderModule.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.johndev.notebook.R
import com.johndev.notebook.navigation.Routes
import com.johndev.notebook.ui.components.IconButtonAppbar
import com.johndev.notebook.ui.components.NoDataView
import com.johndev.notebook.ui.components.NotesCards
import com.johndev.notebook.ui.components.OutlinedTextFielCustom
import com.johndev.notebook.ui.createNoteModule.viewModel.CreateViewModel
import com.johndev.notebook.ui.folderModule.viewModel.FolderViewModel
import com.johndev.notebook.utils.UtilsNotebook

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FolderScreen(
    navigationController: NavHostController,
    folder: String,
    notesViewModel: CreateViewModel,
    folderViewModel: FolderViewModel
) {
    Scaffold(
        topBar = { Header(navigationController, folderViewModel, folder) },
        content = { Body(navigationController, folderViewModel, folder) }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Body(
    navigationController: NavHostController,
    folderViewModel: FolderViewModel,
    folder: String
) {
    folderViewModel.findNotesByFolder(folder = folder)
    val openDialog by folderViewModel.openDialog.observeAsState(false)
    val notesListState by folderViewModel.notesByFolder.observeAsState(initial = emptyList())
    Column(modifier = Modifier.fillMaxSize()) {
        if (notesListState.isNotEmpty()) {
            NotesCards(notesListState, navigationController)
        } else {
            NoDataView(
                textRes = R.string.message_not_found,
                imgRes = R.drawable.img_not_found
            )
        }
        if (openDialog) {
            UpdateAlertDialog(folderViewModel = folderViewModel, folder) {
                folderViewModel.onOpenDialog(it)
            }
        }
    }
}

@Composable
fun Header(
    navigationController: NavHostController,
    folderViewModel: FolderViewModel,
    folder: String
) {
    TopAppBar(
        title = { Text(text = folder) },
        backgroundColor = UtilsNotebook.getColorByTheme(),
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = {
                navigationController.navigate(Routes.HomeScreen.route)
                navigationController.clearBackStack(Routes.FilterScreen.route)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        },
        actions = {
            /*IconButtonAppbar(iconRes = R.drawable.ic_search) {
                navigationController.navigate(Routes.SearchScreen.route)
            }*/
            IconButtonAppbar(iconRes = R.drawable.ic_more_horiz) {
                folderViewModel.onExpandedMenu(true)
            }
            ShowMenu(
                folderViewModel,
                folder = folder,
                navigationController = navigationController
            )
        }
    )
}

@Composable
private fun ShowMenu(
    folderViewModel: FolderViewModel,
    folder: String,
    navigationController: NavHostController
) {
    val expanded: Boolean by folderViewModel.isExpanded.observeAsState(initial = false)
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { folderViewModel.onExpandedMenu(false) }
    ) {
        DropdownMenuItem(onClick = {
            folderViewModel.onOpenDialog(true)
        }) {
            Text(stringResource(R.string.menu_option_rename))
        }
        DropdownMenuItem(onClick = {
            folderViewModel.onDeleteNote(folder)
            navigationController.navigate(Routes.HomeScreen.route)
            navigationController.clearBackStack(Routes.FilterScreen.route)
        }) {
            Text(stringResource(R.string.menu_option_delete))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdateAlertDialog(
    folderViewModel: FolderViewModel,
    folder: String,
    openDialog: (Boolean) -> Unit
) {
    val nameFolder by folderViewModel.folderName.observeAsState(initial = "")
    val isError by folderViewModel.isError.observeAsState(initial = true)
    AlertDialog(
        onDismissRequest = { openDialog(false) },
        title = { Text(text = stringResource(id = R.string.title_update_folder)) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.message_update_folder))
                OutlinedTextFielCustom(
                    text = nameFolder,
                    textRes = stringResource(id = R.string.label_folder_name),
                    styleText = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    isError = isError,
                    onTextChange = {
                        folderViewModel.onNameFolderChange(it)
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
            }
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextButton(
                    modifier = Modifier.weight(1f),
                    onClick = { openDialog(false) }
                ) {
                    Text(stringResource(R.string.btn_dismiss))
                }
                Button(
                    modifier = Modifier.weight(1f),
                    enabled = !isError,
                    onClick = {
                        folderViewModel.onUpdateFolder(folder, nameFolder)
                        openDialog(false)
                    }
                ) {
                    Text(stringResource(R.string.btn_update))
                }
            }
        }
    )
}