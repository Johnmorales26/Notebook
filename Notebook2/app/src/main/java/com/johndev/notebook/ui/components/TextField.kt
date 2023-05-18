package com.johndev.notebook.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.johndev.notebook.utils.UtilsNotebook
import com.johndev.notebook.utils.UtilsNotebook.getColorByTheme
import com.johndev.notebook.entities.FolderEntity


@Composable
private fun colorsTextField(visible: Boolean): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        placeholderColor = getColorByTheme(),
        backgroundColor = getColorByTheme(),
        leadingIconColor = getColorByTheme(),
        unfocusedIndicatorColor = getColorByTheme(),
        focusedIndicatorColor = getColorByTheme(),
        focusedLabelColor = getColorByTheme(),
        unfocusedLabelColor = if (!visible) getColorByTheme() else Color.Gray
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OutlinedTextFielCustom(
    text: String,
    textRes: String,
    styleText: TextStyle,
    modifier: Modifier,
    enabled: Boolean = true,
    entryText: String? = null,
    isError: Boolean = false,
    isSingleLine: Boolean = true,
    keyboardOptions: KeyboardOptions? = null,
    onTextChange: (String) -> Unit,
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = if (enabled) text else UtilsNotebook.getLocalDateAndTime(),
        onValueChange = {
            onTextChange(it)
        },
        label = {
            Text(
                text = textRes,
                style = styleText
            )
        },
        modifier = modifier,
        textStyle = styleText,
        colors = colorsTextField(text == ""),
        enabled = enabled,
        isError = isError,
        keyboardActions = KeyboardActions(onDone = { keyboard?.hide() },
            onNext = { focusManager.moveFocus(FocusDirection.Next) }),
        singleLine = isSingleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardOptions?.keyboardType ?: KeyboardType.Text,
            capitalization = keyboardOptions?.capitalization
                ?: KeyboardCapitalization.Sentences,
            imeAction = if (keyboardOptions == null || keyboardOptions.imeAction == ImeAction.Default)
                ImeAction.Next else keyboardOptions.imeAction
        ),
        )
    AnimatedVisibility(
        visible = isError,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        Text(
            text = "Enter the folder name",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.error
        )
    }
}

@Composable
fun SpinnerFolders(
    modifier: Modifier,
    textRes: String,
    listFoders: List<FolderEntity>,
    onSelectedFolder: (String) -> Unit
) {
    var selectedText by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.wrapContentSize(Alignment.Center)) {
        OutlinedTextField(value = selectedText,
            onValueChange = { selectedText = it },
            enabled = false,
            readOnly = true,
            colors = colorsTextField(selectedText == ""),
            label = {
                Text(
                    text = textRes,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = true })
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            listFoders.forEach {
                DropdownMenuItem(onClick = {
                    isExpanded = false
                    selectedText = it.name
                    onSelectedFolder(selectedText)
                }) {
                    Text(text = it.name)
                }
            }
        }
    }
}