package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zzhoujay.advanceadapter.AdvanceAdapter
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.ui.adapter.holder.LoadMoreHolder

/**
 * Created by zhou on 16-3-26.
 */
abstract class LoadMoreAdapter : AdvanceAdapter {

    var state: LoadMoreHolder.State = LoadMoreHolder.State.ready
        set(value) {
            field = value
            notifyItemChanged(itemCount - 1)
        }

    var onStatusChangeListener: ((state: LoadMoreHolder.State) -> Unit)? = null


    constructor(childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : super(childAdapter) {
    }

    constructor(childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, layoutManager: RecyclerView.LayoutManager) : super(childAdapter, layoutManager) {
    }


    override fun footerCount(): Int = 1

    override fun onFooterCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val holder = LoadMoreHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_load_more, parent, false))
        holder.loadingStateChangeListener = { onStatusChangeListener?.invoke(it) }
        return holder
    }

    override fun onFooterBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is LoadMoreHolder) {
            holder.state = state
        }
        Log.i("onFooterBindViewHolder", "holder:$holder,position:$position,status:$state")
    }

    fun resetState() {
        this.state = LoadMoreHolder.State.ready
    }


}