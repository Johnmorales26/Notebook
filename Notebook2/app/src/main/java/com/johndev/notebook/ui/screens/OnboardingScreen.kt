package com.johndev.notebook.ui.Onboarding

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.johndev.notebook.utils.UtilsNotebook
import com.johndev.notebook.ui.components.OnBoardingPager
import com.johndev.notebook.ui.homeModule.ui.HomeViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboargingScreen(
    navigationController: NavHostController,
    sharedPreferences: SharedPreferences,
    homeViewModel: HomeViewModel
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
    )
    val context = LocalContext.current
    homeViewModel.checkFolders(context)
    OnBoardingPager(
        item = UtilsNotebook.getOnboardingData(), pagerState = pagerState, modifier = Modifier
            .fillMaxWidth()
            .background(color = UtilsNotebook.getColorByTheme()),
        navigationController,
        sharedPreferences
    )
}