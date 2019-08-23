package com.thanatos.app.base

import me.jessyan.autosize.internal.CancelAdapt

abstract class CancelAdaptActivity<T : IBaseView, K : BasePresenter<T>> : BaseActivity<T, K>(), CancelAdapt {


}