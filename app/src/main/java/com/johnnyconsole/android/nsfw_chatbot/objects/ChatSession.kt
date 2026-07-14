package com.johnnyconsole.android.nsfw_chatbot.objects

import kotlinx.serialization.Serializable

@Serializable
data class ChatSession(val personality: Personality, val context: List<ChatMessage>)
