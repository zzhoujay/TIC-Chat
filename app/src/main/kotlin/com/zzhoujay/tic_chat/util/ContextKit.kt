package com.zzhoujay.tic_chat.util

import android.animation.Animator
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
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

fun <T : Context> T.progress(cancelAble: Boolean, msg: CharSequence, t: Dialog.() -> Unit) {
    val progressDialog = ProgressDialog(this)
    progressDialog.setMessage(msg)
    progressDialog.show()
    progressDialog.setCancelable(cancelAble)
    t.invoke(progressDialog)
}

fun Fragment.progress(cancelAble: Boolean, msg: CharSequence, t: Dialog.() -> Unit) {
    val progressDialog = ProgressDialog(context)
    progressDialog.setMessage(msg)
    progressDialog.show()
    progressDialog.setCancelable(cancelAble)
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

interface RealPosition {
    fun realPosition(): (position: Int) -> Int
}

open class SimpleTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
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

