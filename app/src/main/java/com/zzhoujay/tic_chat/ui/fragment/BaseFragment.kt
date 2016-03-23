package com.zzhoujay.tic_chat.ui.fragment

import android.content.Context
import android.support.v4.app.Fragment
import com.zzhoujay.tic_chat.ui.activity.BaseActivity
import com.zzhoujay.tic_chat.ui.activity.ToolBarActivity
import com.zzhoujay.tic_chat.util.Notifier

/**
 * Created by zhou on 16-3-23.
 */
open class BaseFragment : Fragment() {

    var notifier: Notifier<BaseActivity>? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity && context is Notifier<*>) {
            notifier = context
        }
    }

    fun notice(t: BaseActivity.() -> Unit) {
        notifier?.notice(t)
    }

    fun noticeToolBarActivity(t: ToolBarActivity.() -> Unit) {
        notifier?.notice(t as BaseActivity.() -> Unit)
    }

}