package com.zzhoujay.tic_chat.ui.activity

import android.os.Bundle
import com.zzhoujay.tic_chat.ui.fragment.NewTopicFragment
import com.zzhoujay.tic_chat.util.checkLogin

/**
 * Created by zhou on 16-3-31.
 */
class NewTopicActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        checkLogin()
        swipeBack = true
        super.onCreate(savedInstanceState)
        quickFinish = true
        currFragment = NewTopicFragment()

        title="新建主题"
    }
}