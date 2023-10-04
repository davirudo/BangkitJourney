package com.example.simplecleanarchitecture.data

import com.example.simplecleanarchitecture.domain.MessageEntity

class MessageDataSource : IMessageDataSource {
    override fun getMessageFromSource(name: String) =
        MessageEntity("halo $name! selamat datang")
}