package com.zzhoujay.tic_chat.util

import android.animation.Animator
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import cn.bmob.v3.BmobUser
import com.zzhoujay.tic_chat.data.User
import com.zzhoujay.tic_chat.ui.activity.LoginActivity
import org.jetbrains.anko.internals.AnkoInternals
import org.jetbrains.anko.startActivity
import java.io.Serializable

/**
 * Created by zhou on 16-3-23.
 */


fun Fragment.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(res: Int) {
    Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
}

fun <T : Context> T.progress(cancelAble: Boolean, msg: CharSequence, t: ProgressDialog.() -> Unit) {
    val progressDialog = ProgressDialog(this)
    progressDialog.setMessage(msg)
    progressDialog.show()
    progressDialog.setCancelable(cancelAble)
    t.invoke(progressDialog)
}

fun Fragment.progress(cancelAble: Boolean, msg: CharSequence, t: ProgressDialog.() -> Unit) {
    val progressDialog = ProgressDialog(context)
    progressDialog.setMessage(msg)
    progressDialog.setCancelable(cancelAble)
    progressDialog.show()
    t.invoke(progressDialog)
}

fun Fragment.loading(srl: SwipeRefreshLayout, t: SwipeRefreshLayout.() -> Unit) {
    srl.isRefreshing = true
    t.invoke(srl)
}

fun Fragment.withArguments(vararg args: Pair<String, Serializable>) {
    val bundle = Bundle()
    for (p in args) {
        bundle.putSerializable(p.first, p.second)
    }
    arguments = bundle
}

fun Fragment.checkLogin() {
    val user = BmobUser.getCurrentUser(context, User::class.java)
    if (user == null) {
        context.startActivity<LoginActivity>(LoginActivity.flag_login to true)
        activity.finish()
    }
}

fun Activity.checkLogin() {
    val user = BmobUser.getCurrentUser(this, User::class.java)
    if (user == null) {
        startActivity<LoginActivity>(LoginActivity.flag_login to true)
        finish()
    }
}

inline fun <reified T : Activity> Fragment.startActivity(vararg params: Pair<String, Any>) {
    AnkoInternals.internalStartActivity(context, T::class.java, params)
}

open class SimpleTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}

open class AfterTextWatcher(val t: (Editable?) -> Unit) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        t.invoke(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}

open class OnItemSelectedListener(val t: (Int) -> Unit) : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        t.invoke(position)
    }
}

open class SimpleAnimatorListener : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {
    }

    override fun onAnimationEnd(animation: Animator?) {
    }

    override fun onAnimationCancel(animation: Animator?) {
    }

    override fun onAnimationStart(animation: Animator?) {
    }
}

interface Task<T> : Serializable {
    fun doTask(t: T)
}
