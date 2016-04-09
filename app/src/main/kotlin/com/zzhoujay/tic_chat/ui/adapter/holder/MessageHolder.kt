package com.zzhoujay.tic_chat.ui.adapter.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_message.view.*
import org.jetbrains.anko.onClick

/**
 * Created by zhou on 16-3-26.
 */
class MessageHolder(val root: View) :ClickableHolder(root) {

    val icon: ImageView
    val time: TextView
    val content: TextView

    init {
        icon = root.icon
        time = root.time
        content = root.content

        root.onClick {
            onClickListener?.invoke(adapterPosition)
        }
    }
}