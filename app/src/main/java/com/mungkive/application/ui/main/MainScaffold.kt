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

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            // 정확히 'feed' route일 때만
            if (backStackEntry?.destination?.route == Routes.Feed.route) {
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
