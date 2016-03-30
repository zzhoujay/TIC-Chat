package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.data.Topic
import com.zzhoujay.tic_chat.ui.activity.TopicDetailActivity
import com.zzhoujay.tic_chat.ui.adapter.SpinnerAdapter
import com.zzhoujay.tic_chat.ui.adapter.TopicAdapter
import com.zzhoujay.tic_chat.util.loading
import com.zzhoujay.tic_chat.util.toast
import kotlinx.android.synthetic.main.layout_recycler_view.*
import org.jetbrains.anko.startActivity

/**
 * Created by zhou on 16-3-24.
 */
class TopicsFragment : BaseFragment() {

    val topicAdapter: TopicAdapter by lazy {
        val a = TopicAdapter()
        a.onTopicClickListener = {
            context.startActivity<TopicDetailActivity>()
        }
        a
    }
    val spinnerAdapter: SpinnerAdapter by lazy { SpinnerAdapter(topicAdapter) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.layout_recycler_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = spinnerAdapter

        recyclerView.post({
            refresh()
        })

        swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
    }

    fun refresh() {
        loading(swipeRefreshLayout) {
            val query = BmobQuery<Topic>()
            query.setLimit(Configuration.Page.default_size)
            query.order("-updatedAt")
            query.findObjects(context, object : FindListener<Topic>() {
                override fun onError(code: Int, msg: String?) {
                    toast("code:$code,msg:$msg")
                }

                override fun onSuccess(ts: MutableList<Topic>?) {
                    topicAdapter.resetTopic(ts)
                }
            })
        }
    }

    fun loadMore() {
        var query = BmobQuery<Topic>()
        query.setSkip(topicAdapter.itemCount)
        query.setLimit(Configuration.Page.default_size)
        query.order("-updatedAt")
        query.findObjects(context, object : FindListener<Topic>() {
            override fun onError(p0: Int, p1: String?) {
                toast("code:$code,msg:$msg")
            }

            override fun onSuccess(p0: MutableList<Topic>?) {
                topicAdapter.addTopic(p0)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
    }
}