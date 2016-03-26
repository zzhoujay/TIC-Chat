package com.zzhoujay.tic_chat.ui.activity

import android.support.v7.app.AppCompatActivity
import com.zzhoujay.tic_chat.util.Notifier

/**
 * Created by zhou on 16-3-23.
 */
open class BaseActivity : AppCompatActivity(), Notifier<BaseActivity> {
    override fun notice(t: BaseActivity.() -> Unit) {
        t.invoke(this)
    }
}