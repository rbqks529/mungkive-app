package com.mungkive.application.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
    val currentRoute = backStackEntry?.destination?.route

    // 네비게이션이 보일 route만 정의
    val showBottomBarAndFab = when {
        currentRoute == Routes.Feed.route -> true
        currentRoute == Routes.Map.route -> true
        currentRoute == Routes.Tip.route -> true
        currentRoute == Routes.Profile.route -> true
        else -> false // FeedWrite, FeedDetail 등은 숨김
    }

    Scaffold(
        bottomBar = {
            if (showBottomBarAndFab) {
                BottomNavigationBar(navController)
            }
        },
        floatingActionButton = {
            if (currentRoute == Routes.Feed.route && showBottomBarAndFab) {
                FloatingActionButton(
                    onClick = { navController.navigate(Routes.FeedWrite.route) },
                    shape = CircleShape,
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
