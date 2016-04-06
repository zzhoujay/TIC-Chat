package com.zzhoujay.tic_chat.ui.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import com.jude.swipbackhelper.SwipeBackHelper
import com.zzhoujay.tic_chat.util.Notifier

/**
 * Created by zhou on 16-3-23.
 */
open class BaseActivity : AppCompatActivity(), Notifier<BaseActivity> {

    var swipeBack: Boolean = false

    override fun notice(t: BaseActivity.() -> Unit) {
        t.invoke(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (swipeBack)
            SwipeBackHelper.onCreate(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (swipeBack)
            SwipeBackHelper.onPostCreate(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (swipeBack)
            SwipeBackHelper.onDestroy(this)
    }
}