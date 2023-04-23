package com.johndev.notebook.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.johndev.notebook.R
import com.johndev.notebook.entities.OnBoardingData
import com.johndev.notebook.ui.theme.BackgroundDark
import com.johndev.notebook.ui.theme.BackgroundLight
import com.johndev.notebook.ui.theme.PrimaryColor
import com.johndev.notebook.ui.theme.PrimaryDarkColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utils {

    @Composable
    fun isLightTheme(): Boolean {
        // Obtiene el valor del tema actual utilizando isSystemInDarkTheme
        val isSystemDarkTheme = isSystemInDarkTheme()
        // Invierte el valor obtenido para determinar si es un tema claro (light) o no
        return !isSystemDarkTheme
    }

    @Composable
    fun getColorByTheme() = if (isLightTheme()) BackgroundLight else BackgroundDark

    @Composable
    fun getColorDividerByTheme() = if (isLightTheme()) BackgroundDark else BackgroundLight

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLocalDateAndTime(): String {
        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val fechaHoraLocal = LocalDateTime.now()
        return fechaHoraLocal.format(formato)
    }

    fun getOnboardingData(): List<OnBoardingData> {
        return listOf(
            OnBoardingData(
                R.drawable.img_notes,
                "View your notes",
                "Accede a una lista detallada de las notas creadas dentro de la aplicación para tener un mayor control de tus actividades diarias.",
                backgroundColor = PrimaryDarkColor,
                mainColor = PrimaryColor
            ),
            OnBoardingData(
                R.drawable.img_add_notes,
                "Create Notes",
                "Genera notas dentro de la aplicación con el fin de optimizar la organización de tus actividades diarias.",
                backgroundColor = PrimaryDarkColor,
                mainColor = PrimaryColor
            ),
            OnBoardingData(
                R.drawable.img_reminder,
                "Helps to remember",
                "La creación de notas dentro de una aplicación te brindará una mayor facilidad para recordar tus tareas diarias de manera eficiente.",
                backgroundColor = PrimaryDarkColor,
                mainColor = PrimaryColor
            ),
        )
    }

    fun getVersionApp(context: Context): String {
        val packageManager = context.packageManager
        val packageName = context.packageName
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        return packageInfo.versionName
    }

}