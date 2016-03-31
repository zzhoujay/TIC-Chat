package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.util.SimpleTextWatcher
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

            field = value
        }

    var contentOk: Boolean = false
        set(value) {
            field = value
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_new_topic, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postTopicEnable = false

        titleLayout.editText!!.addTextChangedListener(object : SimpleTextWatcher() {

        })

        contentLayout.editText!!.addTextChangedListener(object : SimpleTextWatcher() {

        })

        postTopic.onClick {

        }
    }
}