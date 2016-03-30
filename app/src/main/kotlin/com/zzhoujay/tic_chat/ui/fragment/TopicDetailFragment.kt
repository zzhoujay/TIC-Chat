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
import com.zzhoujay.tic_chat.data.Reply
import com.zzhoujay.tic_chat.data.Topic
import com.zzhoujay.tic_chat.ui.adapter.ReplyAdapter
import com.zzhoujay.tic_chat.ui.adapter.TopicDetailAdapter
import com.zzhoujay.tic_chat.util.loading
import kotlinx.android.synthetic.main.layout_recycler_view.*

/**
 * Created by zhou on 16-3-26.
 */
class TopicDetailFragment : BaseFragment() {

    val replyAdapter: ReplyAdapter by lazy { ReplyAdapter() }
    val topicDetailAdapter: TopicDetailAdapter by lazy { TopicDetailAdapter(replyAdapter) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.layout_recycler_view, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments.containsKey(Topic.TOPIC)) {
            topicDetailAdapter.topic = arguments.getSerializable(Topic.TOPIC) as Topic
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = topicDetailAdapter


    }

    fun refresh() {
        loading(swipeRefreshLayout) {
            val query = BmobQuery<Reply>()
            val size = Configuration.Page.default_size
            query.setLimit(size)
            query.order("-updatedAt")
            query.include("author.profile")
            query.findObjects(context,object :FindListener<Reply>(){
                override fun onSuccess(p0: MutableList<Reply>?) {

                }

                override fun onError(p0: Int, p1: String?) {
                }
            })
        }
    }

    fun loadMore(){

    }


    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
    }
}