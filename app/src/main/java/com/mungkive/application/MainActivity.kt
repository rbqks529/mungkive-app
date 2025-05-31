package com.mungkive.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mungkive.application.core.TokenManager
import com.mungkive.application.network.NetworkModule
import com.mungkive.application.ui.MainApp
//import com.mungkive.application.ui.MainApp
import com.mungkive.application.ui.test.ApiTestScreen
import com.mungkive.application.ui.theme.MungkiveTheme
import com.mungkive.application.viewmodels.ApiTestViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val tokenManager = TokenManager(applicationContext)
//        val loginService = NetworkModule.provideLoginService()
//        val api = NetworkModule.provideApiService(tokenManager, loginService)
//        val viewModel = ApiTestViewModel(api, tokenManager)

        // enableEdgeToEdge()
        setContent {
            MungkiveTheme {
                MainApp()
//                ApiTestScreen(viewModel)
            }
        }
    }
}
