package com.johndev.notebook.entities

import androidx.compose.ui.graphics.Color
import com.johndev.notebook.ui.theme.ColorBlue

data class OnBoardingData(
    val image: Int,
    val title: String,
    val desc: String,
    val backgroundColor:Color,
    val mainColor: Color = ColorBlue
)
