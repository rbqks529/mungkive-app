package com.mungkive.application.navigation

import FeedListView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mungkive.application.models.Routes
import com.mungkive.application.ui.feed.FeedDetailView
import com.mungkive.application.ui.feed.FeedViewModel
import com.mungkive.application.ui.feed.FeedWritingView
import com.mungkive.application.ui.map.MapView
import ProfileView
import com.mungkive.application.ui.tip.TipListView

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Feed.route,
        modifier = modifier
    ) {
        composable(Routes.Map.route) { MapView() }
        composable(Routes.Feed.route) { backStackEntry ->
            // 1. parentEntry로 NavGraph Scope ViewModel을 만듦
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.Feed.route)
            }
            val viewModel: FeedViewModel = viewModel(parentEntry)
            FeedListView(
                viewModel = viewModel,
                onFeedClick = { feed ->
                    navController.navigate("${Routes.DetailFeed.route}/${feed.id}")
                },
                onWriteClick = {}
            )
        }

        composable(Routes.Tip.route) { TipListView() }
        composable(Routes.Profile.route) { ProfileView() }
        composable(Routes.FeedWrite.route) { FeedWritingView(navController) }

        // 상세 페이지
        composable(Routes.DetailFeed.route + "/{feedId}") { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.Feed.route)
            }
            val viewModel: FeedViewModel = viewModel(parentEntry)
            val feedId = backStackEntry.arguments?.getString("feedId") ?: ""
            FeedDetailView(
                feedId = feedId,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}
