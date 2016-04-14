package com.zzhoujay.tic_chat.ui.fragment

import android.app.Activity
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
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.GetListener
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.data.*
import com.zzhoujay.tic_chat.ui.adapter.ReplyAdapter
import com.zzhoujay.tic_chat.ui.adapter.TopicDetailAdapter
import com.zzhoujay.tic_chat.ui.adapter.holder.LoadMoreHolder
import com.zzhoujay.tic_chat.util.*
import kotlinx.android.synthetic.main.fragment_topic_detail.*
import org.jetbrains.anko.async
import org.jetbrains.anko.onClick
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-3-26.
 */
class TopicDetailFragment : ListFragment<Reply>() {


    override val refresh: FindListener<Reply> by lazy {
        object : FindListener<Reply>() {
            override fun onSuccess(p0: MutableList<Reply>?) {
                refreshHandler.invoke(0, null, p0)
            }

            override fun onError(p0: Int, p1: String?) {
                refreshHandler.invoke(p0, p1, null)
            }

        }
    }
    override val loadMore: FindListener<Reply> by lazy {
        object : FindListener<Reply>() {
            override fun onSuccess(p0: MutableList<Reply>?) {
                loadMoreHandler.invoke(0, null, p0)
            }

            override fun onError(p0: Int, p1: String?) {
                loadMoreHandler.invoke(p0, p1, null)
            }
        }
    }

    override var useRecyclerView: RecyclerView by Delegates.notNull<RecyclerView>()
    override var useSwipeRefreshLayout: SwipeRefreshLayout by Delegates.notNull<SwipeRefreshLayout>()
    override var layoutManager: RecyclerView.LayoutManager by Delegates.notNull<RecyclerView.LayoutManager>()

    override val wrapperAdapter: TopicDetailAdapter by lazy {
        val t = TopicDetailAdapter(dataAdapter)
        t.onStatusChangeListener = { if (it == LoadMoreHolder.State.loading) loadMore() }
        t.deleteActionCallback = { deleteCurrTopic() }
        t
    }
    override val dataAdapter: ReplyAdapter by lazy {
        val r = ReplyAdapter()
        r.realPosition = { it - wrapperAdapter.headerCount() }
        r.onItemLongClickListener = {
            atUserSet.add(it.author)
            val replyAt = TextKit.generateColorTextReply("@${it.author.profile.name}", ContextCompat.getColor(context, R.color.material_lightBlue_500))
            replyContent.text.append(" ").append(replyAt).append(" ")
        }
        r
    }
    override val refreshQuerySize: Int = Configuration.Page.default_size
    override val loadMoreQuerySize: Int = Configuration.Page.default_size

    var sendButtonEnable: Boolean
        get() = sendReply.isEnabled
        set(value) {
            sendReply.isEnabled = value
        }

    val atUserSet: HashSet<User> by lazy { HashSet<User>() }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_topic_detail, container, false)
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

        post {
            refreshTopic()
        }
    }

    fun sendReply() {
        progress(false, getString(R.string.alert_send_reply)) {
            val rc = replyContent.text.toString()
            val reply = Reply(rc, null, BmobUser.getCurrentUser(context, User::class.java), wrapperAdapter.topic!!)
            reply.save(context, SimpleSaveListener({ code, msg ->
                dismiss()
                if (code == 0) {
                    val topic = wrapperAdapter.topic
                    val targetUser = topic!!.author
                    val fromUser = BmobUser.getCurrentUser(context, User::class.java)
                    if (!targetUser.equals(fromUser)) {
                        val message = Message(Message.type_reply_topic, fromUser, targetUser, topic, reply)
                        message.save(context, SimpleSaveListener() { code, msg ->
                            if (code == 0) {
                                BmobKit.pushToUser(targetUser, Alert(Alert.type_message, message.objectId))
                            }
                        })
                    }
                    async() {
                        for (user in atUserSet) {
                            if (!user.equals(fromUser) && isUserHasAt(user, rc)) {
                                val message = Message(Message.type_quote_reply, fromUser, user, topic, reply)
                                message.save(context, SimpleSaveListener() { code, msg ->
                                    if (code == 0) {
                                        BmobKit.pushToUser(user, Alert(Alert.type_message, message.objectId))
                                    }
                                })
                            }
                        }
                    }
                    replyContent.text.clear()
                    atUserSet.clear()
                    toast(R.string.toast_reply_success)
                    refresh()
                } else {
                    toast("发送失败")
                }
            }))
        }
    }

    fun refreshTopic() {
        loading(useSwipeRefreshLayout) {
            val query = BmobQuery<Topic>()
            query.include("author.profile")
            query.getObject(context, wrapperAdapter.topic?.objectId, object : GetListener<Topic>() {
                override fun onSuccess(p0: Topic?) {
                    isRefreshing = false
                    wrapperAdapter.topic = p0
                }

                override fun onFailure(p0: Int, p1: String?) {
                    isRefreshing = false
                    Log.i("refreshTopic", "code:$p0,msg:$p1")
                }
            })
        }
    }

    override fun refresh(useCache: Boolean) {
        refreshTopic()
        super.refresh(useCache)
    }

    override fun createQuery(refresh: Boolean): BmobQuery<Reply> {
        val query = BmobQuery<Reply>()
        query.setLimit(if (refresh) refreshQuerySize else loadMoreQuerySize)
        if (!refresh)
            query.setSkip(dataAdapter.itemCount)
        query.include("author.profile")
        query.order("-createdAt")
        query.addWhereEqualTo("topic", wrapperAdapter.topic)
        return query
    }

    private fun isUserHasAt(user: User, text: String): Boolean {
        return text.contains(" @${user.profile.name} ")
    }

    private fun deleteCurrTopic() {
        progress(false, "正在删除中") {
            val topic = wrapperAdapter.topic!!
            topic.setValue("state", Topic.state_deleted)
            topic.update(context, SimpleUpdateListener({ code, msg ->
                dismiss()
                if (code == 0) {
                    activity.setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    toast("删除失败,code:$code,msg:$msg")
                }
            }))
        }
    }


}