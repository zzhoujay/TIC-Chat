package com.zzhoujay.tic_chat.common

/**
 * Created by zhou on 16-3-23.
 */
object Configuration {

    object Profile {
        const val minAccount = 6
        const val maxAccount = 15
        const val minPassword = 6
        const val maxPassword = 15
    }

    object Const {

        const val titleMinLen = 5
        const val titleMaxLen = 40

        const val contentMinLen = 20
        const val contentMaxLen = 1000
    }

    object Page {
        const val default_size = 12
    }

    object Temp {
        const val avatar_temp = "avatar_temp_"
    }

}