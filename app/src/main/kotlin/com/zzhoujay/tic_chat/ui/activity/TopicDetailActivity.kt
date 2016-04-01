package com.zzhoujay.tic_chat.ui.activity

import android.os.Bundle
import com.jude.swipbackhelper.SwipeBackHelper
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Topic
import com.zzhoujay.tic_chat.ui.fragment.TopicDetailFragment
import com.zzhoujay.tic_chat.util.checkLogin
import com.zzhoujay.tic_chat.util.withArguments

/**
 * Created by zhou on 16-3-26.
 */
class TopicDetailActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        checkLogin()
        super.onCreate(savedInstanceState)
        SwipeBackHelper.onCreate(this)
        quickFinish = true

        setTitle(R.string.title_detail)

        val f = TopicDetailFragment()

        if (intent.hasExtra(Topic.TOPIC)) {
            val topic = intent.getSerializableExtra(Topic.TOPIC)
            f.withArguments(Topic.TOPIC to topic)
        }

        currFragment = f
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        SwipeBackHelper.onPostCreate(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        SwipeBackHelper.onDestroy(this)
    }
}