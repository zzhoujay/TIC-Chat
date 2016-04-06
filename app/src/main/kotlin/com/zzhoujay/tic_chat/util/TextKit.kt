package com.zzhoujay.tic_chat.util

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan

/**
 * Created by zhou on 16-4-6.
 */
object TextKit {

    fun generateColorText(text: String, color: Int): CharSequence {
        val sb = SpannableStringBuilder(text)
        sb.setSpan(ForegroundColorSpan(color), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return sb
    }

}