package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zzhoujay.advanceadapter.AdvanceAdapter
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.ui.adapter.holder.LoadMoreHolder

/**
 * Created by zhou on 16-3-26.
 */
abstract class LoadMoreableAdapter : AdvanceAdapter {

    var showLoadMore = true

    constructor(childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : super(childAdapter) {
    }

    constructor(childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, layoutManager: RecyclerView.LayoutManager) : super(childAdapter, layoutManager) {
    }


    override fun footerCount(): Int {
        return if (showLoadMore) 1 else 0
    }

    override fun onFooterCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val holder = LoadMoreHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_load_more, parent, false))
        return holder
    }

}