package com.zzhoujay.tic_chat.ui.weiget

import android.support.v4.content.ContextCompat
import com.zzhoujay.tic_chat.App
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Reply
import com.zzhoujay.tic_chat.util.TextKit

/**
 * Created by zhou on 16-4-6.
 */
class ReplyAt(val reply: Reply) : CharSequence {

    override val length: Int

    val value: CharSequence

    init {
        value = TextKit.generateColorText("@${reply.author.profile?.name}", ContextCompat.getColor(App.app, R.color.material_lightBlue_500))
        length = value.length
    }

    override fun get(index: Int): Char = value[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = value.subSequence(startIndex, endIndex)
}