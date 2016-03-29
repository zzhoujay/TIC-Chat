package com.zzhoujay.tic_chat.util

/**
 * Created by zhou on 16-3-28.
 */

fun <T> List<T>.merge(list: List<T>?): List<T>? {
    if (list == null) {
        return null
    }
    return list.filterNot { this.contains(it) }
}