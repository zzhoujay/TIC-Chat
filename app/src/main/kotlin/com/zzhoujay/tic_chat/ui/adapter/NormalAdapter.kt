package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView

/**
 * Created by zhou on 16-3-30.
 */
abstract class NormalAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var realPosition: (Int) -> Int = { it }
}