package com.zzhoujay.tic_chat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Reply
import com.zzhoujay.tic_chat.ui.adapter.holder.ReplyHolder
import com.zzhoujay.tic_chat.util.DataList
import com.zzhoujay.tic_chat.util.DataListImpl
import com.zzhoujay.tic_chat.util.merge
import java.util.*
import kotlin.reflect.KProperty

/**
 * Created by zhou on 16-3-26.
 */
class ReplyAdapter() : NormalAdapter(), DataList<Reply> {

    var onItemLongClickListener: ((Reply) -> Unit)? = null

    private val replys: MutableList<Reply> by lazy { ArrayList<Reply>() }

    private val onLongClick: ((Int) -> Unit) = {
        val p = realPosition(it)
        onItemLongClickListener?.invoke(replys[p])
    }

    override fun getItemCount(): Int = replys.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val holder = ReplyHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_reply, parent, false))
        holder.onLongClick = onLongClick
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ReplyHolder) {
            val reply = replys[position]
            holder.content.text = reply.content

            val profile = reply.author.profile
            Glide.with(holder.icon.context).load(profile?.avatar?.getFileUrl(holder.icon.context)).into(holder.icon)
        }
    }

    override fun add(t: List<Reply>?) {
        val r = replys.merge(t)
        if (r != null && r.size > 0) {
            val s = itemCount
            replys.addAll(r)
            notifyItemRangeInserted(s, itemCount)
        }
    }

    override fun reset(t: List<Reply>?) {
        replys.clear()
        if (t != null) {
            replys.addAll(t)
        }
        notifyDataSetChanged()
    }

}
