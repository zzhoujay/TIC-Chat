package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import com.zzhoujay.tic_chat.util.merge
import java.util.*

/**
 * Created by zhou on 16-3-30.
 */
abstract class NormalAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var realPosition: (Int) -> Int = { it }

    protected val list: MutableList<T> by lazy { ArrayList<T>() }

    override fun getItemCount(): Int = list.size

    fun add(ts: List<T>?) {
        val r = list.merge(ts)
        if (r != null && r.size > 0) {
            val s = itemCount
            list.addAll(r)
            notifyItemRangeInserted(s, r.size)
        }
    }

    fun reset(ts: List<T>?) {
        list.clear()
        if (ts != null)
            list.addAll(ts)
        notifyDataSetChanged()
    }

    fun addOne(t: T, index: Int = list.size - 1) {
        list.add(index, t)
        notifyItemInserted(index)
    }
}