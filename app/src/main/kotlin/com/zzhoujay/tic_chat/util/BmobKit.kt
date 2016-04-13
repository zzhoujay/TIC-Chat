package com.zzhoujay.tic_chat.util

import android.util.Log
import cn.bmob.v3.BmobInstallation
import cn.bmob.v3.BmobPushManager
import cn.bmob.v3.BmobUser
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.v3.listener.UploadFileListener
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
        Log.i("SimpleSaveListener", "code:$p0,msg:$p1")
        t.invoke(p0, p1)
    }
}


open class SimpleUpdateListener(val t: (code: Int, msg: String?) -> Unit) : UpdateListener() {
    override fun onSuccess() {
        t.invoke(0, null)
    }

    override fun onFailure(p0: Int, p1: String?) {
        Log.i("SimpleUpdateListener", "code:$p0,msg:$p1")
        t.invoke(p0, p1)
    }
}

open class SimpleUploadFileListener(val t: (code: Int, msg: String?) -> Unit) : UploadFileListener() {
    override fun onSuccess() {
        t.invoke(0, null)
    }

    override fun onFailure(p0: Int, p1: String?) {
        Log.i("SimpleUploadFileListener", "code:$p0,msg:$p1")
        t.invoke(p0, p1)
    }
}

object BmobKit {

    fun pushToUser(targetUser: User, alert: Alert) {
        if (BmobUser.getCurrentUser(App.app, User::class.java).equals(targetUser)) {
            return
        }
        val query = BmobInstallation.getQuery<Installation>()
        query.addWhereEqualTo("user", targetUser)
        val push = BmobPushManager<Installation>(App.app)
        push.query = query
        push.pushMessage(alert.toJson())
    }
}

