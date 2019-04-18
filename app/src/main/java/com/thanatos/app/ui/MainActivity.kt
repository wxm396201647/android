package com.thanatos.app.ui

import com.beltaief.flowlayout.FlowLayout
import com.thanatos.app.R
import com.thanatos.app.base.BaseActivity
import com.thanatos.app.mvp.contract.LoginContract
import com.thanatos.app.mvp.presenter.LoginPresenter

class MainActivity : BaseActivity<LoginContract.View, LoginPresenter>(), LoginContract.View{
    override fun getPresenter(): LoginPresenter = LoginPresenter()

    override fun getRootView(): LoginContract.View = this

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initViewsAndEvents() {

    }

    override fun isRegistEventBus(): Boolean = false

    override fun isNeedLec(): FlowLayout? = null

}
