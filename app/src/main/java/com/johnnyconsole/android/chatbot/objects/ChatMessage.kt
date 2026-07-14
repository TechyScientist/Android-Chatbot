package com.johnnyconsole.android.chatbot.objects

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(val sender: MessageSender, val text: String)