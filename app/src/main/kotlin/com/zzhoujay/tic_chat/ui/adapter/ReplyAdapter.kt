package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Reply
import com.zzhoujay.tic_chat.ui.adapter.holder.ReplyHolder
import java.util.*
import kotlin.reflect.KProperty

/**
 * Created by zhou on 16-3-26.
 */
class ReplyAdapter(val size: Int) : NormalAdapter() {

    private val replys: MutableList<Reply> by lazy { ArrayList<Reply>() }

    override fun getItemCount(): Int {
        return size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val holder = ReplyHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_reply, parent, false))
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ReplyHolder) {
            val reply = replys[position]
            holder.content.text = reply.content

            val profile = reply.author.profile
            Glide.with(holder.icon.context).load(profile?.avatar?.getFileUrl(holder.icon.context)).into(holder.icon)
        }
    }

    fun addReply(rs:List<Reply>?){
        if(rs!=null){

        }
    }

}
