package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Topic
import com.zzhoujay.tic_chat.ui.adapter.ReplyAdapter
import com.zzhoujay.tic_chat.ui.adapter.TopicDetailAdapter
import com.zzhoujay.tic_chat.util.loading
import kotlinx.android.synthetic.main.layout_recycler_view.*

/**
 * Created by zhou on 16-3-26.
 */
class TopicDetailFragment : BaseFragment() {

    val replyAdapter: ReplyAdapter by lazy { ReplyAdapter(10) }
    val topicDetailAdapter: TopicDetailAdapter by lazy { TopicDetailAdapter(replyAdapter) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.layout_recycler_view, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = topicDetailAdapter


    }


    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
    }
}