package com.zzhoujay.tic_chat.ui.activity

import android.os.Bundle
import com.zzhoujay.tic_chat.ui.fragment.NewTopicFragment

/**
 * Created by zhou on 16-3-31.
 */
class NewTopicActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quickFinish = true
        currFragment = NewTopicFragment()
    }
}