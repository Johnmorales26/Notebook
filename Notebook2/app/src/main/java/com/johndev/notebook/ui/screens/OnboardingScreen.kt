package com.johndev.notebook.ui.Onboarding

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.johndev.notebook.data.Utils
import com.johndev.notebook.ui.components.OnBoardingPager

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboargingScreen(
    navigationController: NavHostController,
    sharedPreferences: SharedPreferences
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
    )
    OnBoardingPager(
        item = Utils.getOnboardingData(), pagerState = pagerState, modifier = Modifier
            .fillMaxWidth()
            .background(color = Utils.getColorByTheme()),
        navigationController,
        sharedPreferences
    )
}