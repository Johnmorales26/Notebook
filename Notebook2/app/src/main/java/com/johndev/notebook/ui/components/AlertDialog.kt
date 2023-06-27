package com.johndev.notebook.ui.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.johndev.notebook.R
import com.johndev.notebook.ui.homeModule.viewModel.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomAlertDialog(
    homeViewModel: HomeViewModel,
    openDialog: (Boolean) -> Unit
) {
    val msg: Int? by homeViewModel.msg.observeAsState(initial = null)
    val nameFolder by homeViewModel.folderName.observeAsState(initial = "")
    val isError by homeViewModel.isError.observeAsState(initial = true)
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { openDialog(false) },
        title = { Text(text = stringResource(id = R.string.title_create_folder)) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.message_create_folder))
                OutlinedTextFielCustom(
                    text = nameFolder,
                    textRes = stringResource(id = R.string.label_folder_name),
                    styleText = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    isError = isError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    onTextChange = {
                        homeViewModel.onNameFolderChange(it)
                    })
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
                        homeViewModel.saveFolder()
                        msg?.let {
                            Toast.makeText(context, context.getText(msg!!), Toast.LENGTH_SHORT).show()
                        }
                        openDialog(false)
                    }
                ) {
                    Text(stringResource(R.string.btn_create))
                }
            }
        }
    )
}