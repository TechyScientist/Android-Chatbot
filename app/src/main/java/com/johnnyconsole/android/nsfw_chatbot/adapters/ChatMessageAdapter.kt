package com.johnnyconsole.android.nsfw_chatbot.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class ChatMessageAdapter(val context: Context, val items: Array<String>):
    ArrayAdapter<String>(context, 0) {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(index: Int): String {
        return items[index]
    }

    //TODO: Inflate proper view for bot/user messages
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
    }
}