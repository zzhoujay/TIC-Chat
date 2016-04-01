package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.data.Topic
import com.zzhoujay.tic_chat.ui.activity.NewTopicActivity
import com.zzhoujay.tic_chat.ui.activity.TopicDetailActivity
import com.zzhoujay.tic_chat.ui.adapter.SpinnerAdapter
import com.zzhoujay.tic_chat.ui.adapter.TopicAdapter
import com.zzhoujay.tic_chat.ui.adapter.holder.LoadMoreHolder
import com.zzhoujay.tic_chat.util.loading
import kotlinx.android.synthetic.main.fragment_topics.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity

/**
 * Created by zhou on 16-3-24.
 */
class TopicsFragment : BaseFragment() {

    val topicAdapter: TopicAdapter by lazy {
        val a = TopicAdapter()
        a.onTopicClickListener = {
            context.startActivity<TopicDetailActivity>(Topic.TOPIC to it)
        }
        a.realPosition = { it - spinnerAdapter.headerCount() }
        a
    }
    val spinnerAdapter: SpinnerAdapter by lazy {
        val s = SpinnerAdapter(topicAdapter)
        s.onStatusChangeListener = {
            when (it) {
                LoadMoreHolder.State.loading -> {
                    loadMore()
                }
            }
        }
        s
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_topics, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = spinnerAdapter


        new_topic.onClick { context.startActivity<NewTopicActivity>() }

        swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }

        recyclerView.post({ refresh() })
    }

    fun refresh() {
        loading(swipeRefreshLayout) {
            val query = BmobQuery<Topic>()
            spinnerAdapter.resetState()
            val size=Configuration.Page.default_size
            query.setLimit(size)
            query.order("-updatedAt")
            query.include("author.profile")
            query.findObjects(context, object : FindListener<Topic>() {
                override fun onError(code: Int, msg: String?) {
                    isRefreshing = false
                    Log.i("onError", "code:$code,msg:$msg")
                }

                override fun onSuccess(ts: MutableList<Topic>?) {
                    isRefreshing = false
                    val nomoreData = ts?.size ?: 0 < size
                    topicAdapter.reset(ts)
                    spinnerAdapter.state = if (nomoreData) LoadMoreHolder.State.nomore else LoadMoreHolder.State.ready
                }
            })
        }
    }

    fun loadMore() {
        var query = BmobQuery<Topic>()
        query.setSkip(topicAdapter.itemCount)
        val size = Configuration.Page.default_size
        query.setLimit(size)
        query.order("-updatedAt")
        query.include("author.profile")
        query.findObjects(context, object : FindListener<Topic>() {
            override fun onError(code: Int, msg: String?) {
                Log.i("onError", "code:$code,msg:$msg")
                spinnerAdapter.state = LoadMoreHolder.State.error
            }

            override fun onSuccess(p0: MutableList<Topic>?) {
                Log.i("onSuccess", "ts:$p0")
                val nomoreData = p0?.size ?: 0 < size
                topicAdapter.add(p0)
                spinnerAdapter.state = if (nomoreData) LoadMoreHolder.State.nomore else LoadMoreHolder.State.ready
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
    }
}