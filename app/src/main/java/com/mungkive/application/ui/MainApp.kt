package com.mungkive.application.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.mungkive.application.navigation.AppNavHost
import com.mungkive.application.viewmodels.ApiTestViewModel

@Composable
fun MainApp(viewModel: ApiTestViewModel) {
    val navController = rememberNavController()
    var isLoggedIn by rememberSaveable { mutableStateOf(true) } //일단 로그인 성공 화면을 표시

    AppNavHost(
        navController = navController,
        isLoggedIn = isLoggedIn,
        onLoginSuccess = { isLoggedIn = true },
        viewModel = viewModel
    )
}

