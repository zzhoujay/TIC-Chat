package com.zzhoujay.tic_chat.ui.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_topic.view.*
import org.jetbrains.anko.onClick

/**
 * Created by zhou on 16-3-26.
 */

class TopicHolder(val root: View) : RecyclerView.ViewHolder(root) {

    val title: TextView
    val content: TextView
    val name: TextView
    val time: TextView
    val icon: ImageView

    var clickListener: ((position: Int) -> Unit)? = null

    init {
        title = root.title
        time = root.time
        content = root.content
        name = root.name
        icon = root.icon

        root.onClick {
            clickListener?.invoke(adapterPosition)
        }
    }

}