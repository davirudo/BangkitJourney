package com.example.coffeapp.model

import com.example.coffeapp.R

data class Menu(
    val image: Int,
    val title: String,
    val price: String,
)

val dummyMenu = listOf(
    Menu(R.drawable.menu1, "Tiramisu Coffee Milk", "Rp. 15.000"),
    Menu(R.drawable.menu2, "Pumpkin Spice Latte", "Rp. 25.000"),
    Menu(R.drawable.menu3, "Light Cappucino", "Rp. 20.000"),
    Menu(R.drawable.menu4, "Choco Creamy Latte", "Rp. 18.000"),
)

val dummyBestSellerMenu = dummyMenu.shuffled()
