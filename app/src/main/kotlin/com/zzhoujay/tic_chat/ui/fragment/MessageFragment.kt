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
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.data.Message
import com.zzhoujay.tic_chat.data.User
import com.zzhoujay.tic_chat.ui.adapter.MessageAdapter
import com.zzhoujay.tic_chat.ui.adapter.MessageWrapperAdapter
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-3-26.
 */
class MessageFragment : ListFragment<Message>() {

    override var layoutManager: RecyclerView.LayoutManager by Delegates.notNull<RecyclerView.LayoutManager>()
    override var useRecyclerView: RecyclerView by Delegates.notNull<RecyclerView>()
    override var useSwipeRefreshLayout: SwipeRefreshLayout by Delegates.notNull<SwipeRefreshLayout>()

    override val wrapperAdapter: MessageWrapperAdapter by lazy { MessageWrapperAdapter(dataAdapter) }
    override val dataAdapter: MessageAdapter by lazy { MessageAdapter() }
    override val refreshQuery: BmobQuery<Message> by lazy {
        val query = BmobQuery<Message>()
        query.addWhereEqualTo("targetUser", BmobPointer(BmobUser.getCurrentUser(context, User::class.java)))
        query.order("-createdAt")
        query.include("fromUser.profile,targetTopic,targetReply")
        query.setLimit(refreshQuerySize)
        query
    }
    override val loadMoreQuery: BmobQuery<Message> by lazy {
        val query = BmobQuery<Message>()
        query.addWhereEqualTo("targetUser", BmobPointer(BmobUser.getCurrentUser(context, User::class.java)))
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