package com.zzhoujay.tic_chat.ui.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_topic_header.view.*

/**
 * Created by zhou on 16-3-26.
 */
class TopicDetailHolder(val root: View) : RecyclerView.ViewHolder(root) {

    val icon: ImageView
    val name: TextView
    val time: TextView
    val title: TextView
    val content: TextView

    init {
        icon = root.icon
        name = root.name
        time = root.time
        title = root.title
        content = root.content
    }
}