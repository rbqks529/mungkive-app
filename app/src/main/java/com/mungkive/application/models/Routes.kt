package com.mungkive.application.models


sealed class Routes(val route: String, val isRoot: Boolean = true) {
    //Auth 경로
    object Welcome : Routes("Welcome")
    object Login : Routes("Login")
    object Register: Routes("Register")
    object AddProfile : Routes("AddProfile")

    //Main 경로
    object Map : Routes("Map")
    object Feed : Routes("Feed")
    object Tip : Routes("Tip")
    object Profile : Routes("Profile")

    //Detail 경로
    object FeedWrite : Routes("FeedWrite")
    object DetailFeed : Routes("DetailFeed")

    companion object {
        fun getRoutes(route: String): Routes {
            return when(route){
                //Auth
                Welcome.route -> Welcome
                Login.route -> Login
                Register.route -> Register
                //Main
                Map.route -> Map
                Feed.route -> Feed
                Tip.route -> Tip
                Profile.route -> Profile
                //Detail
                FeedWrite.route -> FeedWrite
                DetailFeed.route -> DetailFeed
                else -> Feed
            }
        }
    }
}