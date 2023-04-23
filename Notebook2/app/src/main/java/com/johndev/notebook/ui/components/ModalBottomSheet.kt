package com.johndev.notebook.ui.components

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.johndev.notebook.R
import com.johndev.notebook.data.Utils
import com.johndev.notebook.ui.screens.saveIsHidden
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheet(
    scope: CoroutineScope,
    state: ModalBottomSheetState,
    sharedPreferences: SharedPreferences
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Chip(
                    shape = RoundedCornerShape(8.dp),
                    onClick = { }
                ) {
                    Text(text = "V${Utils.getVersionApp(LocalContext.current)}")
                }
                Text(
                    text = "¡Novedades!",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        scope.launch { state.hide() }
                        saveIsHidden(
                            sharedPreferences = sharedPreferences,
                            isHidden = 0
                        )
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null
                    )
                }
            }
        }
        Text(
            text = stringResource(R.string.new_function_create_folders_v1100),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )
        Text(text = stringResource(R.string.new_function_description_create_folders_v1100))
        Text(
            text = stringResource(R.string.update_bugs),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(vertical = 16.dp),
            fontWeight = FontWeight.Bold
        )
        Text(text = stringResource(R.string.update_bugs_description))
        Text(
            text = stringResource(R.string.update_feedback),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(vertical = 16.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.update_feedback_description),
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                scope.launch { state.hide() }
                saveIsHidden(
                    sharedPreferences = sharedPreferences,
                    isHidden = 0
                )
            }) {
            Text(text = stringResource(R.string.btn_continue))
        }
    }
}