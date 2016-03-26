package com.zzhoujay.tic_chat.util

/**
 * Created by zhou on 16-3-23.
 */
interface Notifier<T> {
    fun notice(t: T.() -> Unit)
}