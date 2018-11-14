package com.thanatos.app.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beltaief.flowlayout.FlowLayout
import com.thanatos.app.utils.EmptyLayoutEnum
import com.thanatos.app.utils.StatusBarUtil
import com.thanatos.app.view.CustomProgressDialog

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


abstract class BaseFragment<T : IBaseView, K : BasePresenter<T>> : Fragment(), IBaseView {

    /**
     * BasePresenter<T>类型 K 用于attachView 、detachView 统一处理
     */
    var mPresenter: K? = null

    /**
     * 统一进度弹窗
     */
    private val progressDialog by lazy { CustomProgressDialog(requireContext()) }

    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false

    /**
     * loading content error 显示布局
     */
    open val emptyLayout by lazy { isNeedLec() }
    /**
     * 视图缓存
     */
    var mView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return mView ?: inflater?.inflate(getLayoutId(), null)
    }
    fun statusBarDark(){
        StatusBarUtil.darkMode(requireActivity())
    }
    fun paddingStatusBar(view:View){
        StatusBarUtil.setPaddingSmart(requireActivity(), view)

    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        mPresenter = getPresenter()
        mPresenter?.attachView(getRootView())
        if (isRegistEventBus()) EventBus.getDefault().register(this)

        initViewsAndEvents()
        lazyLoadDataIfPrepared()
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }


    /**
     * 加载布局
     */
    @LayoutRes
    abstract fun getLayoutId(): Int


    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    /**
     * 获取BasePresenter<T>类型 子类返回实现
     * T 为IBaseView 类型
     */
    abstract fun getPresenter(): K

    /**
     *获取IBaseView类型 子类返回实现
     */
    abstract fun getRootView(): T


    /**
     *初始化View与事件
     */
    abstract fun initViewsAndEvents()

    /**
     * 是否注册EventBus true 注册 false 不注册
     */
    abstract fun isRegistEventBus(): Boolean

    /**
     *显示进度弹窗
     */
    override fun showLoading() {
        progressDialog?.show()
    }

    /**
     * 进度弹窗消失
     */
    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    /***
     * 是否需要LCE 内容
     */
    protected abstract fun isNeedLec(): FlowLayout?

    override fun showCommonView() {
        emptyLayout?.setMode(FlowLayout.CONTENT)
    }

    override fun showNoNetView() {
        emptyLayout?.setMode(FlowLayout.ERROR)
    }

    override fun showErrorView() {
        emptyLayout?.setMode(FlowLayout.ERROR)
    }

    override fun showEmptyView() {
        emptyLayout?.setMode(FlowLayout.EMPTY)
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    open fun onMessageEvent(eventBusCenter: EventBusCenter<JvmType.Object>) {
    }

    /**
     * 释放资源 与 取消掉 页面Presenter 相关请求
     */
    override fun onDestroy() {
        super.onDestroy()
        progressDialog?.dismiss()
        mPresenter?.detachView()
        mView = null
        if (isRegistEventBus()) EventBus.getDefault().unregister(this)
    }

    /**
     * 根据EmptyLayoutEnum 显示EmptyLayout
     */
    open fun setEmptyByType(enumType: EmptyLayoutEnum){
        when (enumType) {
            EmptyLayoutEnum.SHOW_CONTENT -> showCommonView()
            EmptyLayoutEnum.SHOW_NO_NET -> showNoNetView()
            EmptyLayoutEnum.SHOW_ERROR -> showErrorView()
            EmptyLayoutEnum.SHOW_EMPTY -> showEmptyView()

    }
    }
}
