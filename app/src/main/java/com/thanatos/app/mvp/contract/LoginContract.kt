package com.thanatos.app.mvp.contract

import com.thanatos.app.base.IBaseView
import com.thanatos.app.base.IPresenter

interface LoginContract {

    interface View : IBaseView {

    }

    interface Presenter : IPresenter<View> {

        fun test()
    }
}