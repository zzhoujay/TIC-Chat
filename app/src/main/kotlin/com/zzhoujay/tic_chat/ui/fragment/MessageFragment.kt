package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobPointer
import cn.bmob.v3.listener.FindListener
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.data.Message
import com.zzhoujay.tic_chat.data.Topic
import com.zzhoujay.tic_chat.data.User
import com.zzhoujay.tic_chat.ui.activity.TopicDetailActivity
import com.zzhoujay.tic_chat.ui.adapter.MessageAdapter
import com.zzhoujay.tic_chat.ui.adapter.MessageWrapperAdapter
import com.zzhoujay.tic_chat.ui.adapter.holder.LoadMoreHolder
import com.zzhoujay.tic_chat.util.startActivity
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-3-26.
 */
class MessageFragment : ListFragment<Message>() {
    override val refresh: FindListener<Message> by lazy {
        object : FindListener<Message>() {
            override fun onSuccess(p0: MutableList<Message>?) {
                refreshHandler.invoke(0, null, p0)
            }

            override fun onError(p0: Int, p1: String?) {
                refreshHandler.invoke(p0, p1, null)
            }

        }
    }
    override val loadMore: FindListener<Message> by lazy {
        object : FindListener<Message>() {
            override fun onSuccess(p0: MutableList<Message>?) {
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

    override val wrapperAdapter: MessageWrapperAdapter by lazy {
        val m = MessageWrapperAdapter(dataAdapter)
        m.onStatusChangeListener = {
            if (it == LoadMoreHolder.State.loading) {
                loadMore()
            }
        }
        m
    }
    override val dataAdapter: MessageAdapter by lazy {
        val m = MessageAdapter()
        m.onItemClickListener = {
            val topic = it.targetTopic
            startActivity<TopicDetailActivity>(Topic.TOPIC to topic)
        }
        m
    }
    override val refreshQuery: BmobQuery<Message> by lazy {
        val query = BmobQuery<Message>()
        val user=BmobUser.getCurrentUser(context,User::class.java)
        query.addWhereNotEqualTo("fromUser",user)
        query.addWhereEqualTo("targetUser", user)
        query.order("-createdAt")
        query.include("fromUser.profile,targetTopic,targetReply")
        query.setLimit(refreshQuerySize)
        query
    }
    override val loadMoreQuery: BmobQuery<Message> by lazy {
        val query = BmobQuery<Message>()
        val user=BmobUser.getCurrentUser(context,User::class.java)
        query.addWhereNotEqualTo("fromUser",user)
        query.addWhereEqualTo("targetUser", user)
        query.order("-createdAt")
        query.include("fromUser.profile,targetTopic,targetReply")
        val size = Configuration.Page.default_size
        query.setLimit(size)
        query.setSkip(dataAdapter.itemCount)
        query
    }
    override val refreshQuerySize: Int = Configuration.Page.default_size
    override val loadMoreQuerySize: Int = Configuration.Page.default_size


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.layout_recycler_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        useRecyclerView = recyclerView
        useSwipeRefreshLayout = swipeRefreshLayout
        layoutManager = LinearLayoutManager(context)
        super.onViewCreated(view, savedInstanceState)
    }

}