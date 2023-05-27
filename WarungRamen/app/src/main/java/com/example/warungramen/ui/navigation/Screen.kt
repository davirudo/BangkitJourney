package com.example.warungramen.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Order : Screen("order")
    object About : Screen("about")
    object DetailRamen : Screen("home/{ramenId}") {
        fun createRoute(ramenId: Long) = "home/$ramenId"
    }
}
