package com.zzhoujay.tic_chat.ui.adapter.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_reply.view.*
import org.jetbrains.anko.onLongClick

/**
 * Created by zhou on 16-3-26.
 */
class ReplyHolder(val root: View) : ClickableHolder(root) {

    val icon: ImageView
    val content: TextView
    val time: TextView

    init {
        icon = root.icon
        content = root.content
        time = root.time

        root.onLongClick {
            if (onLongClickListener == null) false else {
                onLongClickListener?.invoke(adapterPosition)
                true
            }
        }
    }

}