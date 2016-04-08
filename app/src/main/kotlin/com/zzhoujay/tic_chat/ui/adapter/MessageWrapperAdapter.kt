package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by zhou on 16-4-8.
 */
class MessageWrapperAdapter : LoadMoreAdapter {
    override fun onHeaderBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

    override fun headerCount(): Int = 0

    override fun onHeaderCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? = null

    constructor(childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : super(childAdapter) {
    }

    constructor(childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, layoutManager: RecyclerView.LayoutManager) : super(childAdapter, layoutManager) {
    }
}