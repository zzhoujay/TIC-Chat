package com.zzhoujay.tic_chat.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Category
import com.zzhoujay.tic_chat.ui.adapter.holder.SpinnerHolder
import com.zzhoujay.tic_chat.util.OnItemSelectedListener
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-3-26.
 */
class SpinnerAdapter : LoadMoreAdapter {

    private var adapter: ArrayAdapter<Category>
    var allCategory: Category

    var onItemSelectedListener: ((Category) -> Unit)? = null

    var holder: SpinnerHolder by Delegates.notNull<SpinnerHolder>()

    override fun onFooterBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        super.onFooterBindViewHolder(holder, position)
    }

    constructor(context: Context, childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : super(childAdapter) {
        allCategory = Category("全部")
        adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item)
        adapter.add(allCategory)
    }

    constructor(context: Context, childAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, layoutManager: RecyclerView.LayoutManager) : super(childAdapter, layoutManager) {
        allCategory = Category("全部")
        adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item)
        adapter.add(allCategory)
    }

    override fun headerCount(): Int {
        return 1
    }

    override fun onHeaderCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        holder = SpinnerHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_spinner, parent, false))
        holder.spinner.adapter = adapter
        holder.spinner.onItemSelectedListener = OnItemSelectedListener({
            onItemSelectedListener?.invoke(adapter.getItem(it))
        })
        return holder
    }

    override fun onHeaderBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    fun setCategorys(cs: List<Category>?) {
        adapter.clear()
        adapter.add(allCategory)
        adapter.addAll(cs)
        notifyDataSetChanged()
    }

    fun getCurrCategory(): Category = adapter.getItem(holder.spinner.selectedItemPosition)

}
