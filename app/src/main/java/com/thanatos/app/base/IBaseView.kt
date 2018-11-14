package com.thanatos.app.base


interface IBaseView {

    fun showLoading()

    fun dismissLoading()

    fun showCommonView()

    fun showNoNetView()

    fun showErrorView()

    fun showEmptyView()
}
