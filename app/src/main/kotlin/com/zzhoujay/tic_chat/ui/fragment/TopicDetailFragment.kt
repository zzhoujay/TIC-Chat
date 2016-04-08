package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobPointer
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.data.*
import com.zzhoujay.tic_chat.ui.adapter.ReplyAdapter
import com.zzhoujay.tic_chat.ui.adapter.TopicDetailAdapter
import com.zzhoujay.tic_chat.ui.adapter.holder.LoadMoreHolder
import com.zzhoujay.tic_chat.util.*
import kotlinx.android.synthetic.main.fragment_topic_detail.*
import org.jetbrains.anko.onClick
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-3-26.
 */
class TopicDetailFragment : ListFragment<Reply>() {
    override var useRecyclerView: RecyclerView by Delegates.notNull<RecyclerView>()
    override var useSwipeRefreshLayout: SwipeRefreshLayout by Delegates.notNull<SwipeRefreshLayout>()
    override var layoutManager: RecyclerView.LayoutManager by Delegates.notNull<RecyclerView.LayoutManager>()

    override val wrapperAdapter: TopicDetailAdapter by lazy {
        val t = TopicDetailAdapter(dataAdapter)
        t.onStatusChangeListener = { if (it == LoadMoreHolder.State.loading) loadMore() }
        t
    }
    override val dataAdapter: ReplyAdapter by lazy {
        val r = ReplyAdapter()
        r.realPosition = { it - wrapperAdapter.headerCount() }
        r.onItemLongClickListener = {
            val replyAt = TextKit.generateColorTextReply("@${it.author.profile?.name}", ContextCompat.getColor(context, R.color.material_lightBlue_500))
            replyContent.text.append(" ").append(replyAt).append(" ")
        }
        r
    }
    override val refreshQuery: BmobQuery<Reply> by lazy {
        val query = BmobQuery<Reply>()
        query.setLimit(refreshQuerySize)
        query.order("-createdAt")
        query.include("author.profile")
        query.addWhereEqualTo("topic", BmobPointer(wrapperAdapter.topic))
        query
    }
    override val loadMoreQuery: BmobQuery<Reply> by lazy {
        val query = BmobQuery<Reply>()
        query.setLimit(loadMoreQuerySize)
        query.setSkip(dataAdapter.itemCount)
        query.include("author.profile")
        query.order("-createdAt")
        query.addWhereEqualTo("topic", BmobPointer(wrapperAdapter.topic))
        query
    }
    override val refreshQuerySize: Int = Configuration.Page.default_size
    override val loadMoreQuerySize: Int = Configuration.Page.default_size

    var sendButtonEnable: Boolean
        get() = sendReply.isEnabled
        set(value) {
            sendReply.isEnabled = value
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater?.inflate(R.layout.fragment_topic_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        useRecyclerView = recyclerView
        useSwipeRefreshLayout = swipeRefreshLayout
        layoutManager = LinearLayoutManager(context)
        super.onViewCreated(view, savedInstanceState)

        if (arguments.containsKey(Topic.TOPIC)) {
            wrapperAdapter.topic = arguments.getSerializable(Topic.TOPIC) as Topic
        }

        replyContent.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                sendButtonEnable = s?.length ?: 0 > 4
            }
        })

        sendReply.onClick { sendReply() }

    }

    fun sendReply() {
        progress(false, getString(R.string.alert_send_reply)) {
            val reply = Reply(replyContent.text.toString(), null, BmobUser.getCurrentUser(context, User::class.java), wrapperAdapter.topic!!)
            reply.save(context, SimpleSaveListener({ code, msg ->
                dismiss()
                if (code == 0) {
                    val topic = wrapperAdapter.topic
                    val targetUser = topic!!.author
                    val message = Message(Message.type_reply_topic, BmobUser.getCurrentUser(context, User::class.java), targetUser, topic, reply)
                    message.save(context, SimpleSaveListener() { code, msg ->
                        if (code == 0) {
                            Log.i("send", "id:${message.objectId}")
                            BmobKit.pushToUser(targetUser, Alert(Alert.type_message, message.objectId))
                        }
                    })
                    replyContent.text.clear()
                    toast(R.string.toast_reply_success)
                    refresh()
                } else {
                    Log.i("onError", "code:$code,msg:$msg")
                }
            }))
        }
    }
}