package com.johndev.notebook.ui.components

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.johndev.notebook.ui.theme.PrimaryColor

@Composable
fun FabAddNote(
    textRes: Int,
    iconRes: Int,
    onPress: () -> Unit
) {
    ExtendedFloatingActionButton(
        backgroundColor = PrimaryColor,
        icon = { Icon(painter = painterResource(id = iconRes), contentDescription = null) },
        text = {
            Text(text = stringResource(id = textRes))
        },
        onClick = { onPress() }
    )
}