package com.zzhoujay.tic_chat.util

import android.text.Spannable
import android.text.SpannableStringBuilder
import com.zzhoujay.tic_chat.ui.widget.ReplyAtSpan

/**
 * Created by zhou on 16-4-6.
 */
object TextKit {

    fun generateColorTextReply(text: String, color: Int): CharSequence {
        val sb = SpannableStringBuilder(text)
        sb.setSpan(ReplyAtSpan(color), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return sb
    }

}