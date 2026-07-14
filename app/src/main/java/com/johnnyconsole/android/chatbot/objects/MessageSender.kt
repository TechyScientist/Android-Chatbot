package com.johnnyconsole.android.chatbot.objects

import kotlinx.serialization.Serializable

@Serializable
enum class MessageSender {
    SENDER_USER, SENDER_BOT
}