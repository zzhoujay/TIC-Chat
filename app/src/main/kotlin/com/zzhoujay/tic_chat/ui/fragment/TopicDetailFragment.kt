package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobInstallation
import cn.bmob.v3.BmobPushManager
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobPointer
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.zzhoujay.tic_chat.App
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.data.*
import com.zzhoujay.tic_chat.ui.adapter.ReplyAdapter
import com.zzhoujay.tic_chat.ui.adapter.TopicDetailAdapter
import com.zzhoujay.tic_chat.ui.adapter.holder.LoadMoreHolder
import com.zzhoujay.tic_chat.util.*
import kotlinx.android.synthetic.main.fragment_topic_detail.*
import org.jetbrains.anko.onClick

/**
 * Created by zhou on 16-3-26.
 */
class TopicDetailFragment : BaseFragment() {

    val replyAdapter: ReplyAdapter by lazy {
        val r = ReplyAdapter()
        r.realPosition = { it - topicDetailAdapter.headerCount() }
        r.onItemLongClickListener = {
            val replyAt = TextKit.generateColorTextReply("@${it.author.profile?.name}", ContextCompat.getColor(context, R.color.material_lightBlue_500))
            replyContent.text.append(" ").append(replyAt).append(" ")
        }
        r
    }
    val topicDetailAdapter: TopicDetailAdapter by lazy {
        val t = TopicDetailAdapter(replyAdapter)
        t.onStatusChangeListener = { if (it == LoadMoreHolder.State.loading) loadMore() }
        t
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_topic_detail, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments.containsKey(Topic.TOPIC)) {
            topicDetailAdapter.topic = arguments.getSerializable(Topic.TOPIC) as Topic
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = topicDetailAdapter

        sendReply.onClick { sendReply() }

        swipeRefreshLayout.setOnRefreshListener { refresh() }

        recyclerView.post({ refresh() })
    }

    fun sendReply() {
        progress(false, getString(R.string.alert_send_reply)) {
            val reply = Reply(replyContent.text.toString(), null, BmobUser.getCurrentUser(context, User::class.java), topicDetailAdapter.topic!!)
            reply.save(context, SimpleSaveListener({ code, msg ->
                dismiss()
                if (code == 0) {
                    val topic = topicDetailAdapter.topic
                    val targetUser = topic!!.author
                    val message = Message(BmobUser.getCurrentUser(context, User::class.java), targetUser, topic, reply)
                    BmobKit.pushToUser(targetUser, message)
                    message.save(context)
                } else {
                    Log.i("onError", "code:$code,msg:$msg")
                }
            }))
            reply.save(context, object : SaveListener() {
                override fun onSuccess() {
                    dismiss()
                    replyContent.text.clear()
                    toast(R.string.toast_reply_success)
                }

                override fun onFailure(p0: Int, p1: String?) {
                    dismiss()
                }
            })
        }
    }

    fun refresh() {
        loading(swipeRefreshLayout) {
            val query = BmobQuery<Reply>()
            val size = Configuration.Page.default_size
            query.setLimit(size)
            query.order("-createdAt")
            query.include("author.profile")
            query.addWhereEqualTo("topic", BmobPointer(topicDetailAdapter.topic))
            query.findObjects(context, object : FindListener<Reply>() {
                override fun onSuccess(p0: MutableList<Reply>?) {
                    isRefreshing = false
                    val nomoreData = p0?.size ?: 0 < size
                    replyAdapter.reset(p0)
                    topicDetailAdapter.state = if (nomoreData) LoadMoreHolder.State.nomore else LoadMoreHolder.State.ready
                }

                override fun onError(code: Int, msg: String?) {
                    Log.i("onError", "code:$code,msg:$msg")
                    isRefreshing = false
                }
            })
        }
    }

    fun loadMore() {
        val query = BmobQuery<Reply>()
        val size = Configuration.Page.default_size
        query.setLimit(size)
        query.setSkip(replyAdapter.itemCount)
        query.include("author.profile")
        query.order("-createdAt")
        query.addWhereEqualTo("topic", BmobPointer(topicDetailAdapter.topic))
        query.findObjects(context, object : FindListener<Reply>() {
            override fun onError(code: Int, msg: String?) {
                Log.i("onError", "code:$code,msg:$msg")
            }

            override fun onSuccess(p0: MutableList<Reply>?) {
                Log.i("onSuccess", "ts:$p0")
                val nomoreData = p0?.size ?: 0 < size
                replyAdapter.add(p0)
                topicDetailAdapter.state = if (nomoreData) LoadMoreHolder.State.nomore else LoadMoreHolder.State.ready
            }

        })
    }

    fun atUser(user: User) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
    }
}