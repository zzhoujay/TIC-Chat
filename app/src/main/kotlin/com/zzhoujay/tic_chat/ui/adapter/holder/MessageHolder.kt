package com.zzhoujay.tic_chat.ui.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_message.view.*

/**
 * Created by zhou on 16-3-26.
 */
class MessageHolder(val root: View) : RecyclerView.ViewHolder(root) {

    val icon: ImageView
    val name: TextView
    val time: TextView
    val content: TextView

    init {
        icon = root.icon
        name = root.name
        time = root.time
        content = root.content
    }
}