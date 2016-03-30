package com.zzhoujay.tic_chat.util

import cn.bmob.v3.listener.FindListener

/**
 * Created by zhou on 16-3-29.
 */
open class Result

open class SuccessResult<T>(val t: T?) : Result()

class SuccessResultList<T>(t: List<T>?) : SuccessResult<List<T>>(t)

class ErrorResult(val code: Int, val msg: String?) : Result()

abstract class BmobFindListener<T> : FindListener<T>() {

    abstract fun onResult(result: Result);

    override fun onError(p0: Int, p1: String?) {
        onResult(ErrorResult(p0, p1))
    }

    override fun onSuccess(p0: MutableList<T>?) {
        onResult(SuccessResultList(p0?.toList()))
    }
}