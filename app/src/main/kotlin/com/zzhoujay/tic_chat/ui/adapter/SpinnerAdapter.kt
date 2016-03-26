package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zzhoujay.advanceadapter.AdvanceAdapter
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.ui.adapter.holder.LoadMoreHolder
import com.zzhoujay.tic_chat.ui.adapter.holder.SpinnerHolder

/**
 * Created by zhou on 16-3-26.
 */
class SpinnerAdapter : LoadMoreableAdapter {

    override fun onFooterBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

    }

    constructor(childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : super(childAdapter) {
    }

    constructor(childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, layoutManager: RecyclerView.LayoutManager) : super(childAdapter, layoutManager) {
    }

    override fun headerCount(): Int {
        return 1
    }

    override fun onHeaderCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return SpinnerHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_spinner, parent, false))
    }

    override fun onHeaderBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

}
