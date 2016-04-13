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
import com.zzhoujay.tic_chat.util.ViewKit
import com.zzhoujay.tic_chat.util.loading
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-4-8.
 */
abstract class ListFragment<T> : BaseFragment() {

    abstract var useRecyclerView: RecyclerView
    abstract var useSwipeRefreshLayout: SwipeRefreshLayout

    abstract var layoutManager: RecyclerView.LayoutManager

    abstract val wrapperAdapter: LoadMoreAdapter
    abstract val dataAdapter: NormalAdapter<T>

    abstract val refreshQuerySize: Int
    abstract val loadMoreQuerySize: Int

    abstract val refresh: FindListener<T>
    abstract val loadMore: FindListener<T>

    abstract fun createQuery(refresh: Boolean = false): BmobQuery<T>

    private var cachedRefreshQuery: BmobQuery<T>? = null

    val initAndRefresh: Boolean = true

    val refreshHandler: ((Int, String?, List<T>?) -> Unit) by lazy {
        object : (Int, String?, List<T>?) -> Unit {
            override fun invoke(code: Int, msg: String?, list: List<T>?) {
                useSwipeRefreshLayout.isRefreshing = false
                if (code == 0) {
                    val nomoreData = list?.size ?: 0 < refreshQuerySize
                    dataAdapter.reset(list)
                    wrapperAdapter.state = if (nomoreData) LoadMoreHolder.State.nomore else LoadMoreHolder.State.ready
                    onRefreshSuccess()
                } else {
                    wrapperAdapter.state = LoadMoreHolder.State.error
                    Log.i("onError", "code:$code,msg:$msg")
                }
            }
        }
    }

    val loadMoreHandler: ((Int, String?, List<T>?) -> Unit) by lazy {
        object : (Int, String?, List<T>?) -> Unit {
            override fun invoke(code: Int, msg: String?, list: List<T>?) {
                if (code == 0) {
                    val nomoreData = list?.size ?: 0 < loadMoreQuerySize
                    dataAdapter.add(list)
                    wrapperAdapter.state = if (nomoreData) LoadMoreHolder.State.nomore else LoadMoreHolder.State.ready
                    onLoadMoreSuccess()
                } else {
                    wrapperAdapter.state = LoadMoreHolder.State.error
                    Log.i("onError", "code:$code,msg:$msg")
                }
            }
        }
    }

    open fun onRefreshSuccess() {
    }

    open fun onLoadMoreSuccess() {
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useRecyclerView.adapter = wrapperAdapter
        useRecyclerView.layoutManager = layoutManager

        useSwipeRefreshLayout.setOnRefreshListener { refresh() }
        ViewKit.setSwipeRefreshLayoutColor(useSwipeRefreshLayout)
        if (initAndRefresh)
            useRecyclerView.post { refresh() }

    }

    open fun refresh(useCache: Boolean = true) {
        loading(useSwipeRefreshLayout) {
            wrapperAdapter.resetState()
            if (!(useCache && cachedRefreshQuery != null)) {
                cachedRefreshQuery = createQuery(true)
            }
            cachedRefreshQuery?.findObjects(context, refresh)
        }
    }

    open fun loadMore() {
        createQuery().findObjects(context, loadMore)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        useRecyclerView.adapter = null
    }
}