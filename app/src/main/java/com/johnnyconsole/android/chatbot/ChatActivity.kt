package com.johnnyconsole.android.chatbot

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.johnnyconsole.android.chatbot.adapters.ChatMessageAdapter
import com.johnnyconsole.android.chatbot.databinding.ActivityChatBinding
import com.johnnyconsole.android.chatbot.objects.ChatMessage
import com.johnnyconsole.android.chatbot.objects.ChatSession
import com.johnnyconsole.android.chatbot.objects.MessageSender
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var session: ChatSession
    private lateinit var preferences: SharedPreferences

    private val modelFile = "gemma-4-E2B-it-Uncensored-MAX.litertlm"
    private val format = Json {
        encodeDefaults = true
        prettyPrint = true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        with(binding) {
            enableEdgeToEdge()
            setContentView(root)
            ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
                insets
            }

            preferences = getSharedPreferences("Chatbot", MODE_PRIVATE)
            session = Json.decodeFromString<ChatSession>(preferences.getString("session", "")!!)

            Log.d("PERSONALITY", format.encodeToString(session.personality))

            rvChatMessages.layoutManager = LinearLayoutManager(this@ChatActivity)
            rvChatMessages.adapter = ChatMessageAdapter(this@ChatActivity, session.context)

        }
    }

    override fun onStop() {
        super.onStop()
        preferences.edit { putString("session", Json.encodeToString(session)) }
    }
}