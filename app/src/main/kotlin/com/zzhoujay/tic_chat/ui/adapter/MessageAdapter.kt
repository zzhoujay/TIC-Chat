package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.ui.adapter.holder.MessageHolder

/**
 * Created by zhou on 16-3-26.
 */
class MessageAdapter(val size: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val holder = MessageHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_message, parent, false))
        return holder
    }

    override fun getItemCount(): Int {
        return size
    }


}