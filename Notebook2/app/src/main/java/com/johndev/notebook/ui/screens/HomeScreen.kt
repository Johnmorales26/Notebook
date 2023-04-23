package com.johndev.notebook.ui.screens

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.johndev.notebook.R
import com.johndev.notebook.data.Utils
import com.johndev.notebook.data.Utils.isLightTheme
import com.johndev.notebook.data.ViewModels
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.navigation.Routes
import com.johndev.notebook.ui.components.CardFolders
import com.johndev.notebook.ui.components.CustomAlertDialog
import com.johndev.notebook.ui.components.FabAddNote
import com.johndev.notebook.ui.components.ModalBottomSheet
import com.johndev.notebook.ui.components.NoDataView
import com.johndev.notebook.ui.components.NotesCards
import com.johndev.notebook.ui.components.TopAppBarNotes

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateHomeModalBottomSheet(
    navigationController: NavHostController,
    sharedPreferences: SharedPreferences
) {
    var skipHalfExpanded by remember { mutableStateOf(false) }
    val state = rememberModalBottomSheetState(
        initialValue = getIsHidden(sharedPreferences = sharedPreferences),
        skipHalfExpanded = skipHalfExpanded
    )
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetElevation = 16.dp,
        sheetState = state,
        sheetContent = {
            ModalBottomSheet(scope, state, sharedPreferences)
        }
    ) {
        HomeScreen(navigationController = navigationController)
    }
}

fun saveIsHidden(sharedPreferences: SharedPreferences, isHidden: Int = 1) {
    sharedPreferences.edit {
        putInt("isHidden", isHidden)
        apply()
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun getIsHidden(sharedPreferences: SharedPreferences): ModalBottomSheetValue {
    return if (sharedPreferences.getInt("isHidden", 1) == 1) {
        ModalBottomSheetValue.Expanded
    } else {
        ModalBottomSheetValue.Hidden
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navigationController: NavHostController) {
    navigationController.clearBackStack(Routes.OnboardingScreen.route)
    var state by remember { mutableStateOf(0) }
    var openDialog by remember { mutableStateOf(false) }
    val titles =
        listOf(stringResource(R.string.tab_all_notes), stringResource(R.string.tab_folders))
    Scaffold(
        topBar = {
            TopAppBarNotes(
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
                            modifier = Modifier.background(Utils.getColorByTheme()),
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
            when (state) {
                0 -> {
                    FabAddNote(
                        textRes = R.string.add_new_note,
                        iconRes = R.drawable.ic_add
                    ) {
                        navigationController.navigate(Routes.CreateNoteScreen.route)
                    }
                }

                1 -> {
                    FabAddNote(
                        textRes = R.string.add_new_folder,
                        iconRes = R.drawable.ic_add
                    ) {
                        openDialog = true
                    }
                }
            }
            if (openDialog) {
                CustomAlertDialog {
                    //  TODO: Crear la carpeta y guardarma en SQLite
                    //  TODO: Cambiar el icono de las carpeta por uno mas generico
                    openDialog = it
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    )
}

@Composable
fun AllNotes(navigationController: NavHostController) {
    val notesListState: List<NoteEntity> by ViewModels.getInstanceNotesVM().allNotes.collectAsState(
        initial = emptyList()
    )
    if (notesListState.isEmpty()) {
        NoDataView(
            textRes = R.string.message_all_notes,
            imgRes = R.drawable.img_no_data
        )
    } else {
        NotesCards(notesListState, navigationController)
    }
}

@Composable
fun Folders(navigationController: NavHostController) {
    val isLightTheme = isLightTheme()
    val foldersList: List<FolderEntity> by ViewModels.getInstanceFoldersVM().getAll()
        .collectAsState(
            initial = emptyList()
        )
    if (foldersList.isEmpty()) {
        NoDataView(
            textRes = R.string.message_folders,
            imgRes = R.drawable.ic_folders
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Dos columnas
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(foldersList.size) { index ->
                CardFolders(foldersList[index]) {
                    navigationController.navigate(Routes.FilterScreen.createRoute(it))
                }
            }
        }
    }
}