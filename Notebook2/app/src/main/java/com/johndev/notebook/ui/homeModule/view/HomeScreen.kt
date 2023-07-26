package com.johndev.notebook.ui.homeModule.view

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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.johndev.notebook.R
import com.johndev.notebook.utils.UtilsNotebook
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.navigation.Routes
import com.johndev.notebook.ui.components.CardFolders
import com.johndev.notebook.ui.components.CustomAlertDialog
import com.johndev.notebook.ui.components.FabAddNote
import com.johndev.notebook.ui.components.IconButtonAppbar
import com.johndev.notebook.ui.components.ModalBottomSheet
import com.johndev.notebook.ui.components.NoDataView
import com.johndev.notebook.ui.components.NotesCards
import com.johndev.notebook.ui.createNoteModule.viewModel.CreateViewModel
import com.johndev.notebook.ui.homeModule.viewModel.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateHomeModalBottomSheet(
    navigationController: NavHostController,
    sharedPreferences: SharedPreferences,
    homeViewModel: HomeViewModel,
    notesViewModel: CreateViewModel
) {
    val context = LocalContext.current
    homeViewModel.getIsHidden(sharedPreferences, context)
    val isHidden: ModalBottomSheetValue by homeViewModel.isHidden.observeAsState(initial = ModalBottomSheetValue.Expanded)
    val skipHalfExpanded by remember { mutableStateOf(false) }
    val state = rememberModalBottomSheetState(
        initialValue = isHidden,
        skipHalfExpanded = skipHalfExpanded
    )
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetElevation = 16.dp,
        sheetState = state,
        sheetContent = {
            ModalBottomSheet(scope, state, sharedPreferences, homeViewModel)
        }
    ) {
        HomeScreen(
            navigationController = navigationController,
            homeViewModel = homeViewModel,
            notesViewModel = notesViewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigationController: NavHostController,
    homeViewModel: HomeViewModel,
    notesViewModel: CreateViewModel
) {
    homeViewModel.run {
        getAllNotes()
        getAllFolders()
    }
    val state: Int by homeViewModel.stateTabs.observeAsState(initial = 0)
    val openDialog: Boolean by homeViewModel.openDialog.observeAsState(initial = false)
    Scaffold(
        topBar = { Header(notesViewModel = notesViewModel, navigationController = navigationController) },
        content = { Body(state, homeViewModel, navigationController) },
        floatingActionButton = { Footer(state, openDialog, navigationController, homeViewModel) },
        floatingActionButtonPosition = FabPosition.Center
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Footer(
    state: Int,
    openDialog: Boolean,
    navigationController: NavHostController,
    homeViewModel: HomeViewModel
) {
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
                homeViewModel.onOpenDialog(true)
            }
        }
    }
    if (openDialog) {
        CustomAlertDialog(homeViewModel = homeViewModel) {
            homeViewModel.onOpenDialog(it)
        }
    }
}

@Composable
fun Body(state: Int, homeViewModel: HomeViewModel, navigationController: NavHostController) {
    val titles =
        listOf(stringResource(R.string.tab_all_notes), stringResource(R.string.tab_folders))
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = state,
            modifier = Modifier.padding(vertical = 16.dp),
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.background(UtilsNotebook.getColorByTheme()),
                    text = { Text(title) },
                    selected = state == index,
                    onClick = { homeViewModel.onChangeStateTabs(index) }
                )
            }
        }
        when (state) {
            0 -> AllNotes(
                navigationController = navigationController,
                homeViewModel = homeViewModel
            )

            1 -> Folders(
                navigationController = navigationController,
                homeViewModel = homeViewModel
            )
        }
    }
}

@Composable
fun Header(notesViewModel: CreateViewModel, navigationController: NavHostController) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        backgroundColor = UtilsNotebook.getColorByTheme(),
        elevation = 0.dp,
        actions = {
            /*IconButtonAppbar(iconRes = R.drawable.ic_search) {
                navigationController.navigate(Routes.SearchScreen.route)
            }*/
            IconButtonAppbar(iconRes = R.drawable.ic_more_horiz) {
                notesViewModel.onExpandedMenu(true)
            }
            ShowMenu(notesViewModel = notesViewModel)
        }
    )
}

@Composable
private fun ShowMenu(notesViewModel: CreateViewModel) {
    val expanded: Boolean by notesViewModel.isExpanded.observeAsState(initial = false)
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { notesViewModel.onExpandedMenu(false) }
    ) {
        DropdownMenuItem(onClick = {
            notesViewModel.onRateApp()
        }) {
            Text(stringResource(R.string.menu_option_rate_app))
        }
    }
}

@Composable
fun AllNotes(navigationController: NavHostController, homeViewModel: HomeViewModel) {
    val notesListState: List<NoteEntity> by homeViewModel.allNotes.observeAsState(initial = emptyList())
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
fun Folders(navigationController: NavHostController, homeViewModel: HomeViewModel) {
    val foldersList: List<FolderEntity> by homeViewModel.allFolders.observeAsState(initial = emptyList())
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