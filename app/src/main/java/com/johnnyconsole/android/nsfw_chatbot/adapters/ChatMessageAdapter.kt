package com.johnnyconsole.android.nsfw_chatbot.adapters

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnnyconsole.android.nsfw_chatbot.objects.ChatMessage
import com.johnnyconsole.android.nsfw_chatbot.R.layout.layout_message
import com.johnnyconsole.android.nsfw_chatbot.objects.ChatMessageViewHolder

class ChatMessageAdapter(private val context: Activity, private val items: Array<ChatMessage>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageViewHolder {
        val view = context.layoutInflater.inflate(layout_message, parent, false)
        return ChatMessageViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = items[position]
        (holder as ChatMessageViewHolder).bind(message)
    }

    override fun getItemCount(): Int = items.size


}