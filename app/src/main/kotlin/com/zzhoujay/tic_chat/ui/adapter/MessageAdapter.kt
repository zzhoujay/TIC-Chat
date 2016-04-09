package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Message
import com.zzhoujay.tic_chat.ui.adapter.holder.MessageHolder
import java.util.*

/**
 * Created by zhou on 16-3-26.
 */
class MessageAdapter() : NormalAdapter<Message>() {

    private val onClickListener: ((Int) -> Unit) = {
        val position = realPosition(it)
        onItemClickListener?.invoke(list[position])
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is MessageHolder) {
            val message = list[position]
            val topic = message.targetTopic
            val fromUser = message.fromUser

            Glide.with(holder.icon.context).load(fromUser.profile?.avatar?.getFileUrl(holder.icon.context)).into(holder.icon)
            val content = when (message.type) {
                Message.type_reply_topic -> "\"${fromUser.profile?.name}\"在\"${topic.title}\"中回复了你"
                Message.type_quote_reply -> "\"${fromUser.profile?.name}\"在\"${topic.title}\"中提到了你"
                else -> ""
            }
            holder.content.text = content
            holder.time.text = message.createdAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val holder = MessageHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_message, parent, false))
        holder.onClickListener = onClickListener
        return holder
    }

}