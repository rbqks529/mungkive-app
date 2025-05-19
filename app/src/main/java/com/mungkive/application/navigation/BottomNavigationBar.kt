package com.mungkive.application.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mungkive.application.models.NavBarItems

@Composable
fun BottomNavigationBar(navController: NavController) {

    NavigationBar(
        containerColor = Color(0xFFEBF3FF)
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()  // 현재 backStackEntry의 정보
        val currentRoute = backStackEntry?.destination?.route   // backStackEntry에서 현재 route를 가져올 수 있음

        NavBarItems.BarItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,   // 선택됬을 때 true
                onClick = {
                    navController.navigate(navItem.route) { // 눌렀을 때 화면 이동
                        popUpTo(navController.graph.findStartDestination().id) {    // 홈 빼고 백스텍에서 전부 제거
                            saveState = true    //  composable의 state 저장
                        }
                        launchSingleTop = true  // 현재 루트를 중복해서 추가 X
                        restoreState = true // 저장된 state가 있으면 복구
                    }
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(if (currentRoute == navItem.route) navItem.onSelectedIcon else navItem.selectIcon),
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(text = navItem.title)
                }
            )
        }
    }
}

