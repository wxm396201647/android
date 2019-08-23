package com.thanatos.app.mvp.presenter

import android.util.Log
import com.thanatos.app.base.BasePresenter
import com.thanatos.app.mvp.contract.LoginContract
import com.thanatos.app.mvp.model.LoginModel
import com.thanatos.app.retrofit.CoroutineCallback
import kotlinx.coroutines.*
import com.thanatos.app.extension.handlerResult
import com.thanatos.app.mvp.model.bean.Test
import com.thanatos.app.mvp.model.bean.User
import retrofit2.await


class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter, CoroutineScope by MainScope() {
    private val loginModel: LoginModel by lazy { LoginModel() }

    override fun login() {
        launch(Dispatchers.IO) {
            //            loginModel.login().await().handlerResult(object : CoroutineCallback<BaseBean<User>>() {
//                override fun onFail(bean: BaseBean<User>?): Boolean {
//                    bean?.let {
//                        toastMsg(it.detail, it.message)
//                    }
//                    return true
//                }
//
//                override fun onSuccess(body: BaseBean<User>?) {
//                    mRootView?.let {
//                        body?.let { it1 ->
//                            it.loginSuccess(it1.data)
//                        }
//                    }
//                }
//
//                override fun onError(errorResponseBean: ErrorResponseBean): Boolean {
//                    toastMsg(errorResponseBean.detail, errorResponseBean.message)
//                    return false;
//                }
//            })

            loginModel.test().await().handlerResult(object : CoroutineCallback<Test>() {
                override fun onFail(responseBean: Test?): Boolean {
                    return true
                }

                override fun onSuccess(body: Test?) {
                    Log.e("--------", "success")
                }

            })
        }

    }
}