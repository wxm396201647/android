package com.thanatos.app.mvp.presenter

import com.thanatos.app.api.CommonObserver
import com.thanatos.app.api.ErrorResponseBean
import com.thanatos.app.base.BaseBean
import com.thanatos.app.base.BasePresenter
import com.thanatos.app.mvp.contract.LoginContract
import com.thanatos.app.mvp.model.LoginModel
import com.thanatos.app.mvp.model.bean.LoginBean


class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {
    private val loginModel: LoginModel by lazy { LoginModel() }

    override fun test() {
        checkViewAttached()
        val commonObserver = object : CommonObserver<BaseBean<Void>>() {
            override fun onError(errorResponseBean: ErrorResponseBean): Boolean {

                return false
            }

            override fun onFail(errorResponseBean: BaseBean<Void>?): Boolean {
                return false
            }

            override fun onSuccess(body: BaseBean<Void>?) {

            }


        }
        loginModel.login(LoginBean("", "")).subscribe(commonObserver)
        addSubscription(commonObserver.disposable!!)
    }
}