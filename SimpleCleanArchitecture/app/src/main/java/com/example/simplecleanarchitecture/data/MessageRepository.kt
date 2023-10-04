package com.example.simplecleanarchitecture.data

import com.example.simplecleanarchitecture.domain.IMessageRepository
import com.example.simplecleanarchitecture.domain.MessageEntity

class MessageRepository(private val messageDataSource: IMessageDataSource) : IMessageRepository {
    override fun getWelcomeMessage(name: String) =
        messageDataSource.getMessageFromSource(name)
}