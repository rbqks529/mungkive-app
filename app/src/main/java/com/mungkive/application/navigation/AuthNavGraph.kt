package com.mungkive.application.navigation

import ProfileView
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mungkive.application.models.Routes
import com.mungkive.application.ui.login.LoginView
import com.mungkive.application.ui.login.WelcomeView
import com.mungkive.application.ui.register.RegisterView
import com.mungkive.application.viewmodels.ApiTestViewModel


@Composable
fun AuthNavGraph(
    navController: NavHostController,
    onLoginSuccess: () -> Unit,
    viewModel: ApiTestViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Welcome.route
    ) {
        composable(Routes.Welcome.route) {
            WelcomeView(
                onLoginClick = {
                    navController.navigate(Routes.Login.route)
                },
                onRegisterClick = {
                    navController.navigate(Routes.Register.route)
                }
            )
        }
        composable(Routes.Login.route) {
            LoginView(
                viewModel = viewModel,
            )
        }
        composable(Routes.Register.route) {
            RegisterView(
                viewModel = viewModel
            ) {
                onLoginSuccess() // 인증 성공 처리
            }
        }
        composable(Routes.AddProfile.route) {
            ProfileView(
                /*onProfileSaved = {
                    onLoginSuccess() // 최초 회원가입시 바로 인증 처리
                }*/
            )
        }
    }
}
