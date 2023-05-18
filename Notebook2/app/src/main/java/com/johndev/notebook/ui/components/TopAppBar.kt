package com.johndev.notebook.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.johndev.notebook.navigation.Routes
import com.johndev.notebook.utils.UtilsNotebook.getColorByTheme

@Composable
fun TopAppBarFolders(
    titleRes: Int,
    navIconRes: Int? = null,
    navigationController: NavHostController? = null,
) {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier,
        backgroundColor = getColorByTheme(),
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = {
            if (navIconRes != null) {
                IconButton(onClick = {
                    navigationController?.let {
                        it.navigate(Routes.HomeScreen.route)
                        it.clearBackStack(Routes.CreateNoteScreen.route)
                    }
                }) {
                    Icon(painter = painterResource(id = navIconRes), contentDescription = null)
                }
            }
        }
    )
}