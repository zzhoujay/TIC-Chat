package com.zzhoujay.tic_chat.util

import java.util.*

/**
 * Created by zhou on 16-3-28.
 */

fun <T> MutableList<T>.merge(list: MutableList<T>, f: T.(t: T) -> Int): MutableList<T> {
    addAll(list)
    val l = ArrayList<T>(HashSet<T>(this))
    return l
}