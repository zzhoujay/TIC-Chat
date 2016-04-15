package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Topic
import com.zzhoujay.tic_chat.ui.adapter.holder.TopicHolder

/**
 * Created by zhou on 16-3-26.
 */
class TopicAdapter() : NormalAdapter<Topic>() {

    val onClickListener: ((position: Int) -> Unit) = {
        val rp = realPosition(it)
        onItemClickListener?.invoke(list[rp])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val holder = TopicHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_topic, parent, false))
        holder.onClickListener = onClickListener
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is TopicHolder) {
            val topic = list[position]
            holder.title.text = topic.title
            holder.content.text = topic.content
            holder.time.text = topic.updatedAt
            val userProfile = topic.author!!.profile
            holder.name.text = userProfile.name
            Glide.with(holder.icon.context).load(userProfile.avatar?.getFileUrl(holder.icon.context)).into(holder.icon)
        }
    }

}