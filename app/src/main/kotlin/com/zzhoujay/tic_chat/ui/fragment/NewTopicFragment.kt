package com.zzhoujay.tic_chat.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.listener.FindListener
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.data.Category
import com.zzhoujay.tic_chat.data.Topic
import com.zzhoujay.tic_chat.data.User
import com.zzhoujay.tic_chat.util.*
import kotlinx.android.synthetic.main.fragment_new_topic.*
import org.jetbrains.anko.onClick

/**
 * Created by zhou on 16-3-31.
 */
class NewTopicFragment : BaseFragment() {

    var postTopicEnable: Boolean
        get() = postTopic.isEnabled
        set(value) {
            postTopic.isEnabled = value
        }

    var titleOk: Boolean = false
        set(value) {
            if (value) {
                postTopicEnable = contentOk
            }
            titleLayout.isErrorEnabled = !value
            field = value
        }

    var contentOk: Boolean = false
        set(value) {
            if (value) {
                postTopicEnable = titleOk
            }
            contentLayout.isErrorEnabled = !value
            field = value
        }

    val categoryAdapter: ArrayAdapter<Category> by lazy { ArrayAdapter<Category>(context, android.R.layout.simple_spinner_dropdown_item) }

    var selectedCategory: Category? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_new_topic, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postTopicEnable = false

        titleLayout.error = getString(R.string.error_title_len)
        contentLayout.error = getString(R.string.error_content_len)

        titleLayout.editText!!.addTextChangedListener(AfterTextWatcher() {
            val s = it?.toString() ?: ""
            titleOk = s.length >= Configuration.Const.titleMinLen && s.length <= Configuration.Const.titleMaxLen

        })

        contentLayout.editText!!.addTextChangedListener(AfterTextWatcher() {
            val s = it?.toString() ?: ""
            contentOk = s.length >= Configuration.Const.contentMinLen && s.length <= Configuration.Const.contentMaxLen
        })

        postTopic.onClick {
            progress(false, getString(R.string.alert_post_topic)) {

                val title = titleLayout.editText!!.text.toString()
                val content = contentLayout.editText!!.text.toString()

                val topic = Topic(title, content, 0, BmobUser.getCurrentUser(context, User::class.java), selectedCategory!!,Topic.state_normal)

                topic.save(context, SimpleSaveListener({ code, msg ->
                    dismiss()
                    if (code != 0) {
                        toast("" + msg)
                    } else {
                        toast(R.string.toast_post_topic_success)
                        activity.setResult(Activity.RESULT_OK)
                        finish()
                    }
                }))

            }
        }

        category.adapter = categoryAdapter

        category.onItemSelectedListener = OnItemSelectedListener() {
            selectedCategory = categoryAdapter.getItem(it)
        }

        contentLayout.post { init() }
    }

    fun init() {
        progress(false, getString(R.string.alert_load_info)) {
            val query = BmobQuery<Category>()
            query.order("index")
            query.findObjects(context, object : FindListener<Category>() {
                override fun onError(p0: Int, p1: String?) {
                    dismiss()
                    toast("code:$p0,msg:$p1")
                }

                override fun onSuccess(p0: MutableList<Category>?) {
                    dismiss()
                    selectedCategory = p0?.get(0)
                    categoryAdapter.addAll(p0)
                }
            })
        }
    }
}