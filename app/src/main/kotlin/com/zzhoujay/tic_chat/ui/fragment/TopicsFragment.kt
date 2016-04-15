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
import com.zzhoujay.tic_chat.data.Category
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
import com.zzhoujay.tic_chat.util.toast
import kotlinx.android.synthetic.main.fragment_topics.*
import org.jetbrains.anko.alert
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
        val s = SpinnerAdapter(context, dataAdapter)
        s.onItemSelectedListener = {
            refresh(false)
        }
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
            val intent = Intent(context, TopicDetailActivity::class.java)
            intent.putExtra(Topic.TOPIC, it)
            activity.startActivityForResult(intent, HomeActivity.requestCodeTopicDetail)
        }
        a.realPosition = { it - wrapperAdapter.headerCount() }
        a
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

        post { refreshCategory() }
    }

    fun createQuery(refresh: Boolean = false, category: Category? = null): BmobQuery<Topic> {
        val query = BmobQuery<Topic>()
        if (!refresh)
            query.setSkip(dataAdapter.itemCount)
        query.setLimit(loadMoreQuerySize)
        query.order("-updatedAt")
        query.addWhereEqualTo("state", Topic.state_normal)
        if (category != null && !category.equals(wrapperAdapter.allCategory)) {
            query.addWhereEqualTo("category", category)
        }
        query.include("author.profile")
        return query
    }

    fun refreshCategory() {
        loading(useSwipeRefreshLayout) {
            val query = BmobQuery<Category>()
            query.order("index")
            query.findObjects(context, object : FindListener<Category>() {
                override fun onError(p0: Int, p1: String?) {
                    isRefreshing = false
                    activity.alert {
                        title("提示")
                        message("数据加载失败，请重试")
                        positiveButton("重试") { refreshCategory() }
                        negativeButton("退出") { activity.finish() }
                    }.show()
                }

                override fun onSuccess(p0: MutableList<Category>?) {
                    isRefreshing = false
                    wrapperAdapter.setCategorys(p0)
                    refresh()
                }
            })
        }
    }

    fun gotoTop() {
        useRecyclerView.scrollToPosition(0)
    }

    override fun createQuery(refresh: Boolean): BmobQuery<Topic> {
        return createQuery(refresh, wrapperAdapter.getCurrCategory())
    }

}