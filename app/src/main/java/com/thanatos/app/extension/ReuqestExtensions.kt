package com.thanatos.app.extension

import com.blankj.utilcode.util.LogUtils
import com.thanatos.app.api.ResponseCode
import com.thanatos.app.base.BaseBean
import com.thanatos.app.retrofit.CoroutineCallback
import java.lang.Exception

fun <T> T.handlerResult(callback: CoroutineCallback<T>) {
    try {
        if (this is BaseBean<*>) {
            val bean = this as BaseBean<Any>
            if (ResponseCode.REQUEST_SUCCESS == bean.code) {
                callback.onSuccess(this)
            } else {
                callback.processCode(bean)
                if (!callback.onFail(this)) {
                    LogUtils.e(bean.message)
                }
            }
        } else {
            callback.onSuccess(this)
        }
    } catch (e: Exception) {
        LogUtils.e("Exception:", e.message)
        callback.onError(Exception(e.toString()))
    }
}