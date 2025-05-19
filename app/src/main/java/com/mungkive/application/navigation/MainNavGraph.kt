package com.mungkive.application.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mungkive.application.models.Routes
import com.mungkive.application.ui.feed.FeedListView
import com.mungkive.application.ui.feed.FeedWritingView
import com.mungkive.application.ui.map.MapView
import com.mungkive.application.ui.profile.ProfileView
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
        composable(Routes.Feed.route) { FeedListView() }
        composable(Routes.Tip.route) { TipListView() }
        composable(Routes.Profile.route) { ProfileView() }
        composable(Routes.FeedWrite.route) { FeedWritingView(navController) }

        composable(Routes.Feed.route + "/{feedId}") { backStackEntry ->
            val feedId = backStackEntry.arguments?.getString("feedId") ?: ""
            /*FeedDetailView(feedId)*/
        }
    }
}
