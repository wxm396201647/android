package com.thanatos.app.ui

import androidx.fragment.app.FragmentActivity
import com.beltaief.flowlayout.FlowLayout
import com.blankj.utilcode.util.TimeUtils
import com.thanatos.app.R
import com.thanatos.app.api.APIManager
import com.thanatos.app.base.BaseActivity
import com.thanatos.app.extension.handlerResult
import com.thanatos.app.mvp.contract.LoginContract
import com.thanatos.app.mvp.model.bean.User
import com.thanatos.app.mvp.presenter.LoginPresenter
import com.thanatos.app.retrofit.CoroutineCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

class MainActivity : BaseActivity<LoginContract.View, LoginPresenter>(), LoginContract.View {
    override fun loginSuccess(user: User) {

    }

    override fun loginError() {

    }

    override fun getPresenter(): LoginPresenter = LoginPresenter()

    override fun getRootView(): LoginContract.View = this

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initViewsAndEvents() {
        et_address.setSelection(et_address.text.length)

        request.setOnClickListener {
            launch(Dispatchers.IO) {
                try {
                    APIManager.api.test1(et_address.text.toString()).await().handlerResult(object : CoroutineCallback<String>() {
                        override fun onFail(response: String?): Boolean {
                            showTxt("fail:\n $response")
                            return true
                        }

                        override fun onSuccess(body: String?) {
                            showTxt("success:\n $body")
                        }
                    })
                } catch (e: Exception) {
                    showTxt("fail:\n $e")
                }
            }
        }
    }

    fun showTxt(text: String) {
        launch(Dispatchers.Main) {
            info.text = "${TimeUtils.millis2String(TimeUtils.getNowDate().time)} => $text \n ========================================================================\n ${info.text}"
        }
    }

    override fun isRegistEventBus(): Boolean = false

    override fun isNeedLec(): FlowLayout? = null




}
