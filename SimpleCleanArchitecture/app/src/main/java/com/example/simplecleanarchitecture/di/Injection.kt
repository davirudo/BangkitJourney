package com.example.simplecleanarchitecture.di

import com.example.simplecleanarchitecture.data.IMessageDataSource
import com.example.simplecleanarchitecture.data.MessageDataSource
import com.example.simplecleanarchitecture.data.MessageRepository
import com.example.simplecleanarchitecture.domain.IMessageRepository
import com.example.simplecleanarchitecture.domain.MessageInteractor
import com.example.simplecleanarchitecture.domain.MessageUseCase

object Injection {
    fun provideUseCase(): MessageUseCase {
        val messageRepository = provideRepository()
        return MessageInteractor(messageRepository)
    }

    private fun provideRepository(): IMessageRepository {
        val messageDataSource = provideDataSource()
        return MessageRepository(messageDataSource)
    }

    private fun provideDataSource(): IMessageDataSource {
        return MessageDataSource()
    }
}