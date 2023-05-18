package com.johndev.notebook.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun IconButtonAppbar(iconRes: Int, onClic: () -> Unit) {
    IconButton(onClick = { onClic() }) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null
        )
    }
}