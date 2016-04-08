package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import com.zzhoujay.tic_chat.ui.adapter.LoadMoreAdapter
import com.zzhoujay.tic_chat.ui.adapter.NormalAdapter
import com.zzhoujay.tic_chat.ui.adapter.holder.LoadMoreHolder
import com.zzhoujay.tic_chat.util.loading

/**
 * Created by zhou on 16-4-8.
 */
abstract class ListFragment<T> : BaseFragment() {

    abstract var useRecyclerView: RecyclerView
    abstract var useSwipeRefreshLayout: SwipeRefreshLayout

    abstract var layoutManager: RecyclerView.LayoutManager

    abstract val wrapperAdapter: LoadMoreAdapter
    abstract val dataAdapter: NormalAdapter<T>

    abstract val refreshQuery: BmobQuery<T>
    abstract val loadMoreQuery: BmobQuery<T>
    abstract val refreshQuerySize: Int
    abstract val loadMoreQuerySize: Int

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useRecyclerView.adapter = wrapperAdapter
        useRecyclerView.layoutManager = layoutManager

        useSwipeRefreshLayout.setOnRefreshListener { refresh() }
        useRecyclerView.post { refresh() }
    }

    fun refresh() {
        loading(useSwipeRefreshLayout) {
            wrapperAdapter.resetState()
            refreshQuery.findObjects(context, object : FindListener<T>() {
                override fun onSuccess(p0: MutableList<T>?) {
                    isRefreshing = false
                    val nomoreData = p0?.size ?: 0 < refreshQuerySize
                    dataAdapter.add(p0)
                    wrapperAdapter.state = if (nomoreData) LoadMoreHolder.State.nomore else LoadMoreHolder.State.ready
                }

                override fun onError(code: Int, msg: String?) {
                    isRefreshing = false
                    Log.i("onError", "code:$code,msg:$msg")
                }
            })
        }
    }

    fun loadMore() {
        loadMoreQuery.findObjects(context, object : FindListener<T>() {
            override fun onError(code: Int, msg: String?) {
                wrapperAdapter.state = LoadMoreHolder.State.error
                Log.i("onError", "code:$code,msg:$msg")
            }

            override fun onSuccess(p0: MutableList<T>?) {
                val nomoreData = p0?.size ?: 0 < loadMoreQuerySize
                dataAdapter.add(p0)
                wrapperAdapter.state = if (nomoreData) LoadMoreHolder.State.nomore else LoadMoreHolder.State.ready
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        useRecyclerView.adapter = null
    }
}