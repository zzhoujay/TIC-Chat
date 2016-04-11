package com.zzhoujay.tic_chat.util

import java.util.*

/**
 * Created by zhou on 16-4-12.
 */
object NumKit {

    private val random: Random by lazy { Random() }

    fun randomNums(len: Int):String {
        val sb = StringBuilder()
        for (i in 1..len) {
            sb.append(random.nextInt(10))
        }
        return sb.toString()
    }
}