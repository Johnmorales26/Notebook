package com.johndev.notebook.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.johndev.notebook.R
import com.johndev.notebook.entities.FolderEntity
import com.johndev.notebook.ui.components.OutlinedTextFielCustom
import com.johndev.notebook.ui.components.SpinnerFolders

/*@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsNoteScreen(
    navigationController: NavHostController,
    idNote: Int,
    notesViewModel: NotesViewModel
) {
    val notesState: NoteEntity? by notesViewModel.findNoteById(idNote).collectAsState(
        initial = null
    )
    val context = LocalContext.current
    if (notesState != null) {
        CreateScreen(navigationController = navigationController, notesState, notesViewModel)
    } else {
        Toast.makeText(context, stringResource(R.string.message_note_not_found), Toast.LENGTH_SHORT)
            .show()
    }
}*/

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
            SpinnerFolders(
                modifier = Modifier.weight(2f).padding(bottom = 16.dp),
                textRes = stringResource(R.string.label_select_folder),
                exposedMenu
            ) {
                textState = it
                onValueChange(textState)
            }
        } else {
            OutlinedTextFielCustom(
                text = entryText,
                textRes = stringResource(id = indicationRes),
                styleText = MaterialTheme.typography.body1,
                modifier = Modifier.weight(2f),
                enabled = isEnabled,
                entryText = entryText,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            ) {
                textState = it
                onValueChange(textState)
            }
        }

    }
}
