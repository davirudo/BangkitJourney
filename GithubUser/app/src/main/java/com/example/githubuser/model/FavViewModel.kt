package com.example.githubuser.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.Fav
import com.example.githubuser.repository.FavRepository

class FavViewModel(application: Application): ViewModel() {

    private val mFavRepository: FavRepository = FavRepository(application)

    fun getAllFav(): LiveData<List<Fav>> = mFavRepository.getAllFav()

}