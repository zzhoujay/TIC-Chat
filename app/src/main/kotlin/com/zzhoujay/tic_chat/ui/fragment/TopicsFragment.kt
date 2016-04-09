package com.zzhoujay.tic_chat.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.listener.FindListener
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.data.Topic
import com.zzhoujay.tic_chat.ui.activity.HomeActivity
import com.zzhoujay.tic_chat.ui.activity.NewTopicActivity
import com.zzhoujay.tic_chat.ui.activity.TopicDetailActivity
import com.zzhoujay.tic_chat.ui.adapter.LoadMoreAdapter
import com.zzhoujay.tic_chat.ui.adapter.NormalAdapter
import com.zzhoujay.tic_chat.ui.adapter.SpinnerAdapter
import com.zzhoujay.tic_chat.ui.adapter.TopicAdapter
import com.zzhoujay.tic_chat.ui.adapter.holder.LoadMoreHolder
import com.zzhoujay.tic_chat.util.loading
import kotlinx.android.synthetic.main.fragment_topics.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-3-24.
 */
class TopicsFragment : ListFragment<Topic>() {
    override val refresh: FindListener<Topic> by lazy {
        object : FindListener<Topic>() {
            override fun onSuccess(p0: MutableList<Topic>?) {
                refreshHandler.invoke(0, null, p0)
            }

            override fun onError(p0: Int, p1: String?) {
                refreshHandler.invoke(p0, p1, null)
            }

        }
    }
    override val loadMore: FindListener<Topic> by lazy {
        object : FindListener<Topic>() {
            override fun onSuccess(p0: MutableList<Topic>?) {
                loadMoreHandler.invoke(0, null, p0)
            }

            override fun onError(p0: Int, p1: String?) {
                loadMoreHandler.invoke(p0, p1, null)
            }

        }
    }
    override var layoutManager: RecyclerView.LayoutManager by Delegates.notNull<RecyclerView.LayoutManager>()
    override var useRecyclerView: RecyclerView by Delegates.notNull<RecyclerView>()
    override var useSwipeRefreshLayout: SwipeRefreshLayout by Delegates.notNull<SwipeRefreshLayout>()

    override val wrapperAdapter: SpinnerAdapter by lazy {
        val s = SpinnerAdapter(dataAdapter)
        s.onStatusChangeListener = {
            if (it == LoadMoreHolder.State.loading) {
                loadMore()
            }
        }
        s
    }
    override val dataAdapter: TopicAdapter by lazy {
        val a = TopicAdapter()
        a.onItemClickListener = {
            context.startActivity<TopicDetailActivity>(Topic.TOPIC to it)
        }
        a.realPosition = { it - wrapperAdapter.headerCount() }
        a
    }
    override val refreshQuery: BmobQuery<Topic> by lazy {
        val query = BmobQuery<Topic>()
        query.setLimit(refreshQuerySize)
        query.order("-updatedAt")
        query.include("author.profile")
        query
    }
    override val loadMoreQuery: BmobQuery<Topic> by lazy {
        val query = BmobQuery<Topic>()
        query.setSkip(dataAdapter.itemCount)
        query.setLimit(loadMoreQuerySize)
        query.order("-updatedAt")
        query.include("author.profile")
        query
    }
    override val refreshQuerySize: Int = Configuration.Page.default_size
    override val loadMoreQuerySize: Int = Configuration.Page.default_size


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_topics, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        useRecyclerView = recyclerView
        useSwipeRefreshLayout = swipeRefreshLayout
        layoutManager = LinearLayoutManager(context)
        super.onViewCreated(view, savedInstanceState)
        new_topic.onClick {
            activity.startActivityForResult(Intent(context, NewTopicActivity::class.java), HomeActivity.requestCodeNewTopic)
        }
    }

}