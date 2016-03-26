package com.zzhoujay.tic_chat.ui.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_reply.view.*

/**
 * Created by zhou on 16-3-26.
 */
class ReplayHolder(val root: View) : RecyclerView.ViewHolder(root) {

    val icon: ImageView
    val content: TextView
    val time: TextView

    init {
        icon = root.icon
        content = root.content
        time = root.time
    }

}