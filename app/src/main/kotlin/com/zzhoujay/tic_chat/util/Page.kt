package com.zzhoujay.tic_chat.util

import com.zzhoujay.tic_chat.common.Configuration

/**
 * Created by zhou on 16-3-29.
 */
class Page(var page: Int = 0, var size: Int = Configuration.Page.default_size) {

    fun nextPage(): Page {
        return Page(page + 1, size)
    }

    fun prevPage(): Page {
        return Page(page - 1, size)
    }

    fun skip(): Int {
        return page * size
    }

    fun limit(): Int {
        return size
    }
}