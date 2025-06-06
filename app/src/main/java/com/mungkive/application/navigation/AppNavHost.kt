package com.mungkive.application.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.mungkive.application.ui.main.MainScaffold
import com.mungkive.application.viewmodels.ApiTestViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean,
    onLoginSuccess: () -> Unit,
    viewModel: ApiTestViewModel
) {
    if (!isLoggedIn) {
        // 인증 전 화면
        AuthNavGraph(
            navController = navController,
            onLoginSuccess = onLoginSuccess
        )
    } else {
        // 인증 후 화면
        MainScaffold(
            navController = navController
        )
    }
}

