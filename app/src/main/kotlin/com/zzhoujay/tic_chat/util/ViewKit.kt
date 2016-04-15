package com.zzhoujay.tic_chat.util

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.zzhoujay.tic_chat.App

/**
 * Created by zhou on 16-4-12.
 */
object ViewKit {
    fun setSwipeRefreshLayoutColor(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    fun showSoftInput(view: View) {
        val imm = App.app.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    fun hideSoftInput(view: View) {
        val imm = App.app.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}