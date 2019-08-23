package com.thanatos.app.mvp.contract

import com.thanatos.app.base.IBaseView
import com.thanatos.app.base.IPresenter
import com.thanatos.app.mvp.model.bean.User

interface LoginContract {

    interface View : IBaseView {
        fun loginSuccess(user: User)
        fun loginError()
    }

    interface Presenter : IPresenter<View> {
        fun login()
    }
}