package com.zzhoujay.tic_chat.ui.activity

import android.os.Bundle
import com.jude.swipbackhelper.SwipeBackHelper
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.ui.fragment.TopicDetailFragment

/**
 * Created by zhou on 16-3-26.
 */
class TopicDetailActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SwipeBackHelper.onCreate(this)
        quickFinish = true

        setTitle(R.string.title_detail)

        currFragment = TopicDetailFragment()
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