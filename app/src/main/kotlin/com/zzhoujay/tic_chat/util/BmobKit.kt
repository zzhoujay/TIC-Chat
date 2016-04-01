package com.zzhoujay.tic_chat.util

import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener

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

open class SimpleFindListener<T>(val t: (code: Int, msg: String?, list: List<T>?) -> Unit) : FindListener<T>() {
    override fun onSuccess(p0: List<T>?) {
        t.invoke(0, null, p0)
    }
    override fun onError(p0: Int, p1: String?) {
        t.invoke(p0, p1, null)
    }
}