package com.thanatos.app.ui

import com.beltaief.flowlayout.FlowLayout
import com.thanatos.app.R
import com.thanatos.app.base.BaseActivity
import com.thanatos.app.mvp.contract.LoginContract
import com.thanatos.app.mvp.model.bean.User
import com.thanatos.app.mvp.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity<LoginContract.View, LoginPresenter>(), LoginContract.View {
    override fun loginSuccess(user: User) {

    }

    override fun loginError() {

    }

    override fun getPresenter(): LoginPresenter = LoginPresenter()

    override fun getRootView(): LoginContract.View = this

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initViewsAndEvents() {

        test.setOnClickListener {
            mPresenter?.login()
        }
    }

    override fun isRegistEventBus(): Boolean = false

    override fun isNeedLec(): FlowLayout? = null

}
