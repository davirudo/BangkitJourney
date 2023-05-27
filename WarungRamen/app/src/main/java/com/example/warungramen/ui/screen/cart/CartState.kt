package com.example.warungramen.ui.screen.cart

import com.example.warungramen.model.OrderRamen


data class CartState(
    val orderRamen: List<OrderRamen>,
    val totalRequiredPoint: Int
)