package com.example.warungramen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.warungramen.data.RamenRepository
import com.example.warungramen.ui.screen.cart.CartViewModel
import com.example.warungramen.ui.screen.detail.DetailRamenViewModel
import com.example.warungramen.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: RamenRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailRamenViewModel::class.java)) {
            return DetailRamenViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}