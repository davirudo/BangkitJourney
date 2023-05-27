package com.example.warungramen.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailRamen : Screen("home/{ramenId}") {
        fun createRoute(ramenId: Long) = "home/$ramenId"
    }
}
