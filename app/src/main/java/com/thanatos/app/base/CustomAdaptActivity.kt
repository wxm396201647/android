package com.thanatos.app.base

import me.jessyan.autosize.internal.CustomAdapt


abstract class CustomAdaptActivity<T : IBaseView, K : BasePresenter<T>> : BaseActivity<T, K>(), CustomAdapt {

    override fun isBaseOnWidth(): Boolean {
        return false
    }

    override fun getSizeInDp(): Float {
        return 667f
    }
}