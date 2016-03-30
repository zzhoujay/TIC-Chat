package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Topic
import com.zzhoujay.tic_chat.ui.adapter.holder.TopicHolder
import com.zzhoujay.tic_chat.util.merge
import java.util.*

/**
 * Created by zhou on 16-3-26.
 */
class TopicAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val topics: MutableList<Topic>

    init {
        topics = ArrayList<Topic>()
    }

    var onTopicClickListener: ((position: Int) -> Unit)? = null

    val onItemClickListener: ((position: Int) -> Unit) = {
        onTopicClickListener?.invoke(it)
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
        if(holder is TopicHolder){
            val topic=topics[position]
            holder.title.text=topic.title
            holder.content.text=topic.content
            holder.time.text=topic.updatedAt
            val user=topic.author
            holder.name.text=user.username
        }
    }

    fun addTopic(ts: List<Topic>?) {
        val t = topics.merge(ts)
        if (t != null) {
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