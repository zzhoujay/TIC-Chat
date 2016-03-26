package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.ui.adapter.holder.TopicDetailHolder

/**
 * Created by zhou on 16-3-26.
 */
class TopicDetailAdapter : LoadMoreableAdapter {


    constructor(childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : super(childAdapter) {
    }

    constructor(childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, layoutManager: RecyclerView.LayoutManager) : super(childAdapter, layoutManager) {
    }

    override fun onHeaderBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

    override fun headerCount(): Int = 1

    override fun onFooterBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

    override fun onHeaderCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val holder = TopicDetailHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_topic_header, parent, false))

        return holder
    }

    override fun onFooterCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return super.onFooterCreateViewHolder(parent, viewType)
    }
}