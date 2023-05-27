package com.example.warungramen.di

import com.example.warungramen.data.RamenRepository


object Injection {
    fun provideRepository(): RamenRepository {
        return RamenRepository.getInstance()
    }
}