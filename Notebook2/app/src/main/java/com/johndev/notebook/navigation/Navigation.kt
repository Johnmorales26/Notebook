package com.johndev.notebook.navigation

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.johndev.notebook.R
import com.johndev.notebook.data.Utils.getColorByTheme
import com.johndev.notebook.data.Utils.getColorDividerByTheme
import com.johndev.notebook.data.Utils.getLocalDateAndTime
import com.johndev.notebook.data.Utils.getOnboardingData
import com.johndev.notebook.data.ViewModels.getInstanceFoldersVM
import com.johndev.notebook.data.ViewModels.getInstanceNotesVM
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.ui.components.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboargingScreen(
    navigationController: NavHostController,
    sharedPreferences: SharedPreferences
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
    )
    OnBoardingPager(
        item = getOnboardingData(), pagerState = pagerState, modifier = Modifier
            .fillMaxWidth()
            .background(color = getColorByTheme()),
        navigationController,
        sharedPreferences
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navigationController: NavHostController) {
    navigationController.clearBackStack(Routes.OnboardingScreen.route)
    var state by remember { mutableStateOf(0) }
    val titles =
        listOf(stringResource(R.string.tab_all_notes), stringResource(R.string.tab_folders))
    Scaffold(
        topBar = {
            TopAppBarCustom(
                titleRes = R.string.app_name,
                navigationController = navigationController
            ) {

            }
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TabRow(
                    selectedTabIndex = state,
                    modifier = Modifier.padding(vertical = 16.dp),
                ) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            modifier = Modifier.background(getColorByTheme()),
                            text = { Text(title) },
                            selected = state == index,
                            onClick = { state = index }
                        )
                    }
                }
                when (state) {
                    0 -> AllNotes(navigationController = navigationController)
                    1 -> Folders(navigationController = navigationController)
                }
            }
        },
        floatingActionButton = {
            FabAddNote(
                textRes = R.string.add_new_note,
                iconRes = R.drawable.ic_add
            ) {
                navigationController.navigate(Routes.CreateScreen.route)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    )
}

@Composable
fun AllNotes(navigationController: NavHostController) {
    val notesListState: List<NoteEntity> by getInstanceNotesVM().allNotes.collectAsState(initial = emptyList())
    if (notesListState.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_no_data),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.message_all_notes),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Justify
            )
        }
    } else {
        NotesCards(notesListState, navigationController)
    }
}

@Composable
fun Folders(navigationController: NavHostController) {
    val context = LocalContext.current
    if (getInstanceFoldersVM().allFolders.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_folders),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.message_folders),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Justify
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Dos columnas
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(getInstanceFoldersVM().allFolders.size) { index ->
                CardFolders(getInstanceFoldersVM().allFolders[index]) {
                    navigationController.navigate(Routes.FilterScreen.createRoute(it))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FilterScreen(navigationController: NavHostController, filter: String) {
    val notesListState: List<NoteEntity> by getInstanceNotesVM().findByFolder(filter)
        .collectAsState(initial = emptyList())
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
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_not_found),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.message_not_found),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsNoteScreen(navigationController: NavHostController, idNote: Int) {
    val notesState: NoteEntity? by getInstanceNotesVM().findById(idNote).collectAsState(
        initial = null
    )
    val context = LocalContext.current
    if (notesState != null) {
        CreateScreen(navigationController = navigationController, notesState)
    } else {
        Toast.makeText(context, stringResource(R.string.message_note_not_found), Toast.LENGTH_SHORT)
            .show()
    }
}

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
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBarCustom(
                titleRes = R.string.all_notes,
                navIconRes = R.drawable.ic_arrow_back,
                navigationController = navigationController,
                deleteNote = noteEntity != null
            ) {
                getInstanceNotesVM().delete(noteEntity!!)
                navigationController.clearBackStack(Routes.CreateScreen.route)
                navigationController.navigate(Routes.HomeScreen.route)
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Divider(color = getColorDividerByTheme().copy(alpha = 0.1f))
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
                    categoryRes = R.string.folder,
                    indicationRes = R.string.label_write_name_author,
                    isEnabled = false,
                    isVisibleMenu = true,
                    exposedMenu = getInstanceFoldersVM().allFolders,
                    entryText = folderState
                )
                {
                    folderState = it
                }
                Divider(color = getColorDividerByTheme().copy(alpha = 0.1f))
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
                                lastModified = getLocalDateAndTime(),
                                tags = "",
                                folder = folderState,
                                content = contentState
                            )
                            getInstanceNotesVM().insert(note)
                        } else {
                            // Update Note
                            noteEntity.apply {
                                title = titleState
                                createdBy = createdByState
                                lastModified = getLocalDateAndTime()
                                tags = ""
                                folder = folderState
                                content = contentState
                            }
                            getInstanceNotesVM().update(noteEntity)
                        }
                        navigationController.clearBackStack(Routes.CreateScreen.route)
                        navigationController.navigate(Routes.HomeScreen.route)
                    } else {
                        Toast.makeText(context, "Fill all the fields", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormData(
    categoryRes: Int,
    indicationRes: Int,
    isEnabled: Boolean = true,
    isVisibleMenu: Boolean = false,
    exposedMenu: List<FolderEntity> = listOf(),
    entryText: String,
    onValueChange: (String) -> Unit,
) {
    var textState by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = categoryRes),
            modifier = Modifier.weight(1f)
        )
        if (isVisibleMenu) {
            ActOutlinedTextField(
                modifier = Modifier.weight(2f),
                textRes = stringResource(R.string.label_select_folder),
                exposedMenu
            ) {
                textState = it
                onValueChange(textState)
            }
        } else {
            OutlinedTextFielCustom(
                textRes = stringResource(id = indicationRes),
                styleText = MaterialTheme.typography.body1,
                modifier = Modifier.weight(2f),
                enabled = isEnabled,
                entryText = entryText
            ) {
                textState = it
                onValueChange(textState)
            }
        }

    }
}
