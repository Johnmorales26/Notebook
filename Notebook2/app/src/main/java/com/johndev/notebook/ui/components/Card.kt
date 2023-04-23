package com.johndev.notebook.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.johndev.notebook.R
import com.johndev.notebook.data.Utils.isLightTheme
import com.johndev.notebook.data.ViewModels.getInstanceFoldersVM
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.entities.NoteEntity
import com.johndev.notebook.navigation.Routes

@Composable
fun CardNote(noteEntity: NoteEntity, onNotePress: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable { onNotePress(noteEntity.id) }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = noteEntity.lastModified,
                style = MaterialTheme.typography.caption
            )
            Text(
                text = noteEntity.title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(vertical = 8.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = noteEntity.folder,
                style = MaterialTheme.typography.caption
            )
            Text(
                text = noteEntity.content,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun CardFolders(folderEntity: FolderEntity, onPressFolder: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .height(120.dp)
            .clickable { onPressFolder(folderEntity.name) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(56.dp).padding(8.dp),
                painter = painterResource(id = if (isLightTheme()) R.drawable.ic_folder else R.drawable.ic_folder_white),
                contentDescription = null
            )
            Text(text = folderEntity.name)
        }
    }
}

@Composable
fun NotesCards(notesListState: List<NoteEntity>, navigationController: NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Dos columnas
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(notesListState.size) { index ->
            CardNote(notesListState[index]) {
                navigationController.navigate(Routes.DetailsNoteScreen.createRoute(it))
            }
        }
    }
}