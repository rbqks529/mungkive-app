package com.mungkive.application.models

data class BarItem(
    val title: String,
    val selectIcon: Int,  //Int (리소스 id)
    val onSelectedIcon: Int,
    val route: String
)


