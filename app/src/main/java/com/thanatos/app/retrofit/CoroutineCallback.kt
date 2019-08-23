package com.thanatos.app.retrofit

import com.blankj.utilcode.util.LogUtils
import com.thanatos.app.api.ErrorResponseBean
import com.thanatos.app.api.ResponseCode
import com.thanatos.app.base.BaseBean
import retrofit2.*
import java.lang.Exception

abstract class CoroutineCallback<T> {
    //业务错误
    abstract fun onFail(responseBean: T?): Boolean

    fun onError(e: Throwable) {
        LogUtils.e(e.message)
    }

    abstract fun onSuccess(body: T?)

    fun processCode(errorResponseBean: BaseBean<Any>): Boolean {
        when (errorResponseBean.code) {
            ResponseCode.ACCESS_TOKEN_INVALID -> LogUtils.e(errorResponseBean.message)
            ResponseCode.UPDATE_FORCE -> LogUtils.e(errorResponseBean.message)
            ResponseCode.TICKET_UNAVALIBLE2 -> LogUtils.e(errorResponseBean.message)
            ResponseCode.TICKET_UNAVALIBLE3 -> LogUtils.e(errorResponseBean.message)
            ResponseCode.TOKEN_EMPTY -> LogUtils.e(errorResponseBean.message)
            ResponseCode.LOGIN_DUPLICATE, ResponseCode.TICKET_UNAVALIBLE -> {

//                BaseContext.instance.logout()
//                EventBus.getDefault().post(EventBusCenter<Int>(Constants.Tag.QUIT_LOGIN))
//                val intent = Intent( BaseContext.instance.applicationContext, LoginActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                BaseContext.instance.applicationContext.startActivity(intent)

                return true
            }
        }
        return false
    }
}