package com.thanatos.app.api

import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.thanatos.app.base.BaseBean
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.Response

abstract class CommonObserver<T> : Observer<Response<T>> {
    var disposable:Disposable? = null

    override fun onComplete() {

    }

    override fun onSubscribe(disposable: Disposable) {
        this.disposable = disposable
    }

    override fun onNext(response: Response<T>) {
        when {
            ResponseCode.SUCCESS == response.code() ->{
                try {

                    val baseBean = response.body() as BaseBean<Any>

                    if( ResponseCode.SUCCESS == baseBean.code){
                        try {
                            onSuccess(response.body())
                        }catch (e : Exception){
                            LogUtils.e("Exception:",e.toString())
                        }

                    }else{

                        processCode(baseBean)
                        if(!onFail(response.body())){
                            LogUtils.e(baseBean.message)
                        }

                    }
                }catch (e:Exception){
                    LogUtils.e("Exception:",e.message)

                }


            }
            ResponseCode.BAD_REQUEST == response.code() || ResponseCode.INTERINAL_SERVER_ERROR == response.code() -> {
                val errorResponseBean: ErrorResponseBean
                try {
                    errorResponseBean = Gson().fromJson(response.errorBody()?.string(), ErrorResponseBean::class.java)
                    onError(errorResponseBean)
                } catch (e: Exception) {
                    onError(e)
                }


            }
            else -> run { onError(Exception("unknown error")) }
        }

    }

    abstract fun onError(errorResponseBean: ErrorResponseBean): Boolean

    //业务错误
    abstract fun onFail(errorResponseBean: T?): Boolean

    abstract fun onSuccess(body: T?)

    override fun onError(e: Throwable) {
       LogUtils.e(e.message)
    }


    fun processCode( errorResponseBean: BaseBean<Any>): Boolean {
        when (errorResponseBean.code) {
            ResponseCode.ACCESS_TOKEN_INVALID -> LogUtils.e(errorResponseBean.message)
            ResponseCode.UPDATE_FORCE -> LogUtils.e(errorResponseBean.message)
            ResponseCode.TICKET_UNAVALIBLE2 -> LogUtils.e(errorResponseBean.message)
            ResponseCode.TICKET_UNAVALIBLE3 -> LogUtils.e(errorResponseBean.message)
            ResponseCode.TOKEN_EMPTY -> LogUtils.e(errorResponseBean.message) 
            ResponseCode.LOGIN_DUPLICATE , ResponseCode.TICKET_UNAVALIBLE -> {

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