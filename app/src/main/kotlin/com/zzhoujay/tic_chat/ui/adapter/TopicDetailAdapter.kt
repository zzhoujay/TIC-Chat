package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobUser
import com.bumptech.glide.Glide
import com.zzhoujay.tic_chat.App
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Topic
import com.zzhoujay.tic_chat.data.User
import com.zzhoujay.tic_chat.ui.adapter.holder.TopicDetailHolder
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-3-26.
 */
class TopicDetailAdapter : LoadMoreAdapter {

    var topic: Topic ? = null
        set(value) {
            field = value
            notifyItemChanged(0)
        }

    var deleteActionCallback: (() -> Unit)? = null
    var iconClickListener:(()->Unit)?=null

    constructor(childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : super(childAdapter) {
    }

    constructor(childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, layoutManager: RecyclerView.LayoutManager) : super(childAdapter, layoutManager) {
    }

    override fun onHeaderBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is TopicDetailHolder) {
            holder.title.text = topic?.title
            holder.content.text = topic?.content
            holder.time.text = topic?.updatedAt
            val profile = topic?.author?.profile
            holder.name.text = profile?.name
            holder.deleteAction.visibility = if (BmobUser.getCurrentUser(App.app, User::class.java).equals(topic?.author)) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            Glide.with(holder.icon.context).load(profile?.avatar?.getFileUrl(holder.icon.context)).into(holder.icon)
        }
    }

    override fun headerCount(): Int = 1

    override fun onFooterBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        super.onFooterBindViewHolder(holder, position)
    }

    override fun onHeaderCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val holder = TopicDetailHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_topic_header, parent, false))
        holder.onClickListener = {
            deleteActionCallback?.invoke()
        }
        holder.onIconClickListener={
            iconClickListener?.invoke()
        }
        return holder
    }

    override fun onFooterCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return super.onFooterCreateViewHolder(parent, viewType)
    }

}