package com.mungkive.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mungkive.application.ui.MainApp
import com.mungkive.application.ui.theme.MungkiveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MungkiveTheme {
                MainApp()
            }
        }
    }
}