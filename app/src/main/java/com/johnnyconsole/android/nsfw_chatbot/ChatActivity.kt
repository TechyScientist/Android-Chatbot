package com.johnnyconsole.android.nsfw_chatbot

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.johnnyconsole.android.nsfw_chatbot.adapters.ChatMessageAdapter
import com.johnnyconsole.android.nsfw_chatbot.databinding.ActivityChatBinding
import com.johnnyconsole.android.nsfw_chatbot.objects.ChatMessage
import com.johnnyconsole.android.nsfw_chatbot.objects.MessageSender

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val modelFile = "gemma-4-E2B-it-Uncensored-MAX.litertlm"

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

            val array = arrayOf(
                ChatMessage(MessageSender.SENDER_BOT, "From the Bot!"),
                ChatMessage(MessageSender.SENDER_USER, "Fron the User!"),
                ChatMessage(
                    MessageSender.SENDER_BOT,
                    "This is a really long message from the bot and it should wrap to the next like, allowing us to test the width of the message box. Hopefully it will work as I intend it to :)"
                ),
                ChatMessage(
                    MessageSender.SENDER_USER,
                    "This is a really long message from the user and it should wrap to the next like, allowing us to test the width of the message box. Hopefully it will work as I intend it to :)"
                ),
            )

            rvChatMessages.layoutManager = LinearLayoutManager(this@ChatActivity)
            rvChatMessages.adapter = ChatMessageAdapter(this@ChatActivity, array)

        }
    }
}