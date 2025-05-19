package com.mungkive.application.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mungkive.application.models.Routes
import com.mungkive.application.navigation.BottomNavigationBar
import com.mungkive.application.navigation.MainNavGraph

@Composable
fun MainScaffold(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember(backStackEntry) {
        derivedStateOf {
            backStackEntry?.destination?.route?.let {
                Routes.getRoutes(it)
            } ?: run {
                Routes.Feed
            }
        }
    }
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            if (currentRoute == Routes.Feed) {
                FloatingActionButton(
                    onClick = { navController.navigate(Routes.FeedWrite.route) },
                    containerColor = Color(0xFFEBF3FF)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        }
    ) { innerPadding ->
        MainNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
