package com.zzhoujay.tic_chat.ui.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zzhoujay.tic_chat.App
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Reply
import com.zzhoujay.tic_chat.ui.adapter.holder.ReplyHolder
import com.zzhoujay.tic_chat.util.TextKit

/**
 * Created by zhou on 16-3-26.
 */
class ReplyAdapter() : NormalAdapter<Reply>() {

    var onItemLongClickListener: ((Reply) -> Unit)? = null

    private val onLongClick: ((Int) -> Unit) = {
        val p = realPosition(it)
        onItemLongClickListener?.invoke(list[p])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val holder = ReplyHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_reply, parent, false))
        holder.onLongClickListener = onLongClick
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ReplyHolder) {
            val reply = list[position]
            holder.content.text = TextKit.generateAtColor(reply.content,ContextCompat.getColor(App.app,R.color.material_lightBlue_500))

            val profile = reply.author.profile
            Glide.with(holder.icon.context).load(profile?.avatar?.getFileUrl(holder.icon.context)).into(holder.icon)
        }
    }

}
