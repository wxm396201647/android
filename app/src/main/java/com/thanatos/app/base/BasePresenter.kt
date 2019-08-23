package com.thanatos.app.base

import kotlinx.coroutines.*

open class BasePresenter<T : IBaseView> : IPresenter<T> {

    var mRootView: T? = null
        private set

    val scope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }


    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    override fun detachView() {
        mRootView = null
        scope.cancel()
    }

    private val isViewAttached: Boolean
        get() = mRootView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")

    fun toastMsg(detail: String?, message: String?) : String {
        return if (detail.isNullOrEmpty()) message!! else detail
    }
}