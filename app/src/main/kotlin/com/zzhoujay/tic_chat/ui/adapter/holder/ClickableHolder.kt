package com.zzhoujay.tic_chat.ui.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by zhou on 16-4-9.
 */
open class ClickableHolder(root: View) : RecyclerView.ViewHolder(root) {
    var onClickListener: ((Int) -> Unit)? = null
    var onLongClickListener: ((Int) -> Unit)? = null
}