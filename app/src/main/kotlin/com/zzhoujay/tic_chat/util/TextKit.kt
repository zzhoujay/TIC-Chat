package com.zzhoujay.tic_chat.util

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.zzhoujay.tic_chat.ui.widget.ReplyAtSpan
import java.util.regex.Pattern

/**
 * Created by zhou on 16-4-6.
 */
object TextKit {

    fun generateColorTextReply(text: String, color: Int): CharSequence {
        val sb = SpannableStringBuilder(text)
        sb.setSpan(ReplyAtSpan(color), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return sb
    }

    val atUserPattern: Pattern by lazy { Pattern.compile("( @\\w+ )") }

    fun generateAtColor(text: String, color: Int): CharSequence {
        val m = atUserPattern.matcher(text)
        val ssb = SpannableStringBuilder(text)
        val sp = ForegroundColorSpan(color)
        while (m.find()) {
            ssb.setSpan(sp, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return ssb
    }

}