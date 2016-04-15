package com.zzhoujay.tic_chat.ui.adapter.holder

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_topic_header.view.*
import org.jetbrains.anko.onClick

/**
 * Created by zhou on 16-3-26.
 */
class TopicDetailHolder(val root: View) : ClickableHolder(root) {

    val icon: ImageView
    val name: TextView
    val time: TextView
    val title: TextView
    val content: TextView
    val deleteAction: ImageButton

    var onIconClickListener:(()->Unit)?=null

    init {
        icon = root.icon
        name = root.name
        time = root.time
        title = root.title
        content = root.content
        deleteAction = root.deleteAction

        deleteAction.onClick {
            onClickListener?.invoke(adapterPosition)
        }
        icon.onClick {
            onIconClickListener?.invoke()
        }
    }
}