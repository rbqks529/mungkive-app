package com.mungkive.application.models

import com.mungkive.application.R

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "지도",
            selectIcon = R.drawable.ic_map,
            onSelectedIcon = R.drawable.ic_map_on,
            route = Routes.Map.route
        ),
        BarItem(
            title = "피드",
            selectIcon = R.drawable.ic_feed,
            onSelectedIcon = R.drawable.ic_feed_on,
            route = Routes.Feed.route
        ),
        BarItem(
            title = "팁",
            selectIcon = R.drawable.ic_tip,
            onSelectedIcon = R.drawable.ic_tip_on,
            route = Routes.Tip.route
        ),
        BarItem(
            title = "프로핑",
            selectIcon = R.drawable.ic_profile,
            onSelectedIcon = R.drawable.ic_profile_on,
            route = Routes.Profile.route
        )
    )
}