package com.johndev.notebook.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.johndev.notebook.data.Utils
import com.johndev.notebook.data.Utils.getColorByTheme
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
        disabledLabelColor = getColorByTheme(),
        disabledTextColor = getColorByTheme(),
        disabledPlaceholderColor = getColorByTheme(),
        disabledIndicatorColor = getColorByTheme(),
        unfocusedLabelColor = if (!visible) getColorByTheme() else Color.Gray
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OutlinedTextFielCustom(
    textRes: String,
    styleText: TextStyle,
    modifier: Modifier,
    enabled: Boolean = true,
    entryText: String? = null,
    onTextChange: (String) -> Unit,
) {
    var textState by remember { mutableStateOf(entryText ?: "") }
    OutlinedTextField(
        value = if (enabled) textState else Utils.getLocalDateAndTime(),
        onValueChange = {
            textState = it
            onTextChange(textState)
        },
        label = {
            Text(
                text = textRes,
                style = styleText
            )
        },
        modifier = modifier,
        textStyle = styleText,
        colors = colorsTextField(textState == ""),
        enabled = enabled
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActOutlinedTextField(
    modifier: Modifier,
    textRes: String,
    listFoders: List<FolderEntity>,
    onSelectedFolder: (String) -> Unit
) {
    var selectedText by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {

    }) {
        OutlinedTextField(
            modifier = modifier,
            value = selectedText, onValueChange = {
                isExpanded = true
                selectedText = it
                onSelectedFolder(selectedText)
            },
            label = {
                Text(
                    text = textRes,
                )
            },
            colors = colorsTextField(selectedText == "")
        )
        val filterInOptions =
            listFoders.filter { it.name.contains(selectedText, ignoreCase = true) }
        if (filterInOptions.isNotEmpty()) {
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                filterInOptions.forEach { selectionOption ->
                    DropdownMenuItem(onClick = {
                        selectedText = selectionOption.name
                        isExpanded = false
                        onSelectedFolder(selectedText)
                    }) {
                        Text(text = selectionOption.name)
                    }
                }
            }
        }
    }

}