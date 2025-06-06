package com.mungkive.application.navigation

import FeedListView
import ProfileView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mungkive.application.models.Routes
import com.mungkive.application.ui.feed.FeedDetailView
import com.mungkive.application.ui.feed.FeedViewModel
import com.mungkive.application.ui.feed.FeedAddView
import com.mungkive.application.ui.map.MapView
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mungkive.application.ui.tip.TipListView
import com.mungkive.application.viewmodels.ApiTestViewModel

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ApiTestViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Feed.route,
        modifier = modifier
    ) {
        composable(Routes.Map.route) {
            val feedViewModel: FeedViewModel = viewModel()
            val feedList by feedViewModel.feedList.collectAsState()

            // ViewModel에서 데이터를 불러오도록 호출
            LaunchedEffect(Unit) {
                feedViewModel.fetchFeeds()
            }

            MapView(
                feedList = feedList,
                onFeedSelected = { feedId ->
                    navController.navigate("${Routes.DetailFeed.route}/$feedId")
                }
            )
        }
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
                }
            )
        }

        composable(Routes.Tip.route) { TipListView() }
        composable(Routes.Profile.route) {
            ProfileView(
                viewModel = viewModel,
                onProfileRegistered = { }
            )
        }
        composable(Routes.FeedWrite.route) { FeedAddView(navController) }

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
