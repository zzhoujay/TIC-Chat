package com.zzhoujay.tic_chat.util

import cn.bmob.v3.BmobInstallation
import cn.bmob.v3.BmobPushManager
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.zzhoujay.tic_chat.App
import com.zzhoujay.tic_chat.data.Alert
import com.zzhoujay.tic_chat.data.Installation
import com.zzhoujay.tic_chat.data.User

/**
 * Created by zhou on 16-3-29.
 */
open class Result

open class SuccessResult<T>(val t: T?) : Result()

class SuccessResultList<T>(t: List<T>?) : SuccessResult<List<T>>(t)

class ErrorResult(val code: Int, val msg: String?) : Result()

open class SimpleSaveListener(val t: (code: Int, msg: String?) -> Unit) : SaveListener() {
    override fun onSuccess() {
        t.invoke(0, null)
    }

    override fun onFailure(p0: Int, p1: String?) {
        t.invoke(p0, p1)
    }
}


open class SimpleUpdateListener(val t: (code: Int, msg: String?) -> Unit) : UpdateListener() {
    override fun onSuccess() {
        t.invoke(0, null)
    }

    override fun onFailure(p0: Int, p1: String?) {
        t.invoke(p0, p1)
    }
}

object BmobKit {

    fun pushToUser(user: User, alert: Alert) {
        val query = BmobInstallation.getQuery<Installation>()
        query.addWhereEqualTo("user", user)
        val push = BmobPushManager<Installation>(App.app)
        push.query = query
        push.pushMessage(alert.toJson())
    }
}

