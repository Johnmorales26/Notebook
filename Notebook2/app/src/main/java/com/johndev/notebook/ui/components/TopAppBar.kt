package com.johndev.notebook.ui.components

import android.widget.Toast
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.johndev.notebook.R
import com.johndev.notebook.data.Utils.getColorByTheme
import com.johndev.notebook.navigation.Routes

@Composable
fun TopAppBarCustom(
    titleRes: Int,
    navIconRes: Int? = null,
    navigationController: NavHostController? = null,
    deleteNote: Boolean = false,
    onDeletePress: () -> Unit
) {
    var context = LocalContext.current
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
                        it.clearBackStack(Routes.CreateScreen.route)
                    }
                }) {
                    Icon(painter = painterResource(id = navIconRes), contentDescription = null)
                }
            }
        },
        actions = {
            if (deleteNote) {
                IconButton(onClick = {
                    onDeletePress()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = null
                    )
                }
            } else {
                IconButton(onClick = {
                    Toast.makeText(context, "New Features Coming Soon", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                }
                IconButton(onClick = {
                    Toast.makeText(context, "New Features Coming Soon", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_horiz),
                        contentDescription = null
                    )
                }
            }
        }
    )
}