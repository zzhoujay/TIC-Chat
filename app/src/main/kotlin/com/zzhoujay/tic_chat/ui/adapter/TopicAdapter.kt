package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.ui.adapter.holder.TopicHolder

/**
 * Created by zhou on 16-3-26.
 */
class TopicAdapter(val size: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        return size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }


}