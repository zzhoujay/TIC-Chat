package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Topic
import com.zzhoujay.tic_chat.ui.adapter.holder.TopicHolder
import com.zzhoujay.tic_chat.util.merge
import java.util.*

/**
 * Created by zhou on 16-3-26.
 */
class TopicAdapter() : NormalAdapter() {


    private val topics: MutableList<Topic> by lazy { ArrayList<Topic>() }

    var onTopicClickListener: ((Topic) -> Unit)? = null

    val onItemClickListener: ((position: Int) -> Unit) = {
        val rp = realPosition(it)
        onTopicClickListener?.invoke(topics[rp])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val holder = TopicHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_topic, parent, false))
        holder.clickListener = onItemClickListener
        return holder
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is TopicHolder) {
            val topic = topics[position]
            holder.title.text = topic.title
            holder.content.text = topic.content
            holder.time.text = topic.updatedAt
            val userProfile = topic.author.profile
            holder.name.text = userProfile?.name
            Glide.with(holder.icon.context).load(userProfile?.avatar?.getFileUrl(holder.icon.context)).into(holder.icon)
        }
    }

    fun addTopic(ts: List<Topic>?) {
        val t = topics.merge(ts)
        if (t != null && t.size > 0) {
            val s = itemCount
            topics.addAll(t)
            notifyItemRangeInserted(s, t.size)
        }
    }

    fun resetTopic(ts: List<Topic>?) {
        topics.clear()
        if (ts != null)
            topics.addAll(ts)
        notifyDataSetChanged()
    }

}