package com.johnnyconsole.android.chatbot.objects

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.johnnyconsole.android.chatbot.R.id.tvMessageText
import com.johnnyconsole.android.chatbot.objects.MessageSender.*
import com.johnnyconsole.android.chatbot.R.drawable.dr_background_message_bot
import com.johnnyconsole.android.chatbot.R.drawable.dr_background_message_user

class ChatMessageViewHolder(private val context: Activity, private val messageView: View):
    RecyclerView.ViewHolder(messageView) {

    fun bind(message: ChatMessage) {
        //Find the TextView and get its layout parameters
        val text = messageView.findViewById<TextView>(tvMessageText)
        val params = text.layoutParams as ConstraintLayout.LayoutParams

        //Add the message text
        text.text = message.text

        //Find the correct background based on who sent the message and add the appropriate tail
        val backgroundDrawable = AppCompatResources.getDrawable(
            context,
            if (message.sender == SENDER_USER) dr_background_message_user
            else dr_background_message_bot
        ) as GradientDrawable
        val radiusPx = 20 * context.resources.displayMetrics.density
        if (message.sender == SENDER_USER) {
            backgroundDrawable.cornerRadii = floatArrayOf(
                radiusPx, radiusPx,
                radiusPx, radiusPx,
                0f, 0f,
                radiusPx, radiusPx
            )
        } else {
            backgroundDrawable.cornerRadii = floatArrayOf(
                radiusPx, radiusPx,
                radiusPx, radiusPx,
                radiusPx, radiusPx,
                0f, 0f
            )
        }
        text.background = backgroundDrawable

        //Force message bubbles to 3/4 of the max width
        val displayMetrics = context.resources.displayMetrics
        val maxChatWidth = (3 * displayMetrics.widthPixels) / 4
        text.maxWidth = maxChatWidth

        if (message.sender == SENDER_USER) {
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            params.startToStart = ConstraintLayout.LayoutParams.UNSET
        } else {
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            params.endToEnd = ConstraintLayout.LayoutParams.UNSET
        }
        text.layoutParams = params
    }

}