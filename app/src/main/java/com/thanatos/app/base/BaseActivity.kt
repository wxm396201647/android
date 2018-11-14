package com.thanatos.app.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.beltaief.flowlayout.FlowLayout
import com.blankj.utilcode.util.KeyboardUtils
import com.thanatos.app.R
import com.thanatos.app.utils.EmptyLayoutEnum
import com.thanatos.app.utils.StatusBarUtil
import com.thanatos.app.view.CustomProgressDialog
import com.umeng.analytics.MobclickAgent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


abstract class BaseActivity<T : IBaseView, K : BasePresenter<T>> : AppCompatActivity(), IBaseView {
    /**
     * BasePresenter<T>类型 K 用于attachView 、detachView 统一处理
     */
    var mPresenter: K? = null

    /**
     * 统一进度弹窗
     */
    private val progressDialog by lazy { CustomProgressDialog(this) }

    /**
     * loading content error 显示布局
     */
    open val emptyLayout by lazy { isNeedLec() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mPresenter = getPresenter()


        mPresenter?.attachView(getRootView())
        if (isRegistEventBus()) EventBus.getDefault().register(this)

        initViewsAndEvents()
    }

    fun statusBarDark(){
        StatusBarUtil.darkMode(this)
    }
    fun paddingStatusBar(view:View){
        StatusBarUtil.setPaddingSmart(this, view)

    }
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
     * 获取资源id 子类返回
     */
    abstract fun getLayoutId(): Int

    /**
     *初始化View与事件
     */
    abstract fun initViewsAndEvents()

    /**
     * 是否注册EventBus true 注册 false 不注册
     */
    abstract fun isRegistEventBus(): Boolean

    /***
     * 是否需要LCE 内容
     */
    protected abstract fun isNeedLec(): FlowLayout?

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
        mPresenter?.detachView()
        progressDialog?.dismiss()
        if (isRegistEventBus()) EventBus.getDefault().unregister(this)
        // 收起输入框
        KeyboardUtils.hideSoftInput(this)
    }


    /**
     * 根据EmptyLayoutEnum 显示EmptyLayout
     */
    open fun setEmptyByType(enumType: EmptyLayoutEnum){
        when(enumType){
            EmptyLayoutEnum.SHOW_CONTENT -> showCommonView()
            EmptyLayoutEnum.SHOW_NO_NET -> showNoNetView()
            EmptyLayoutEnum.SHOW_ERROR -> showErrorView()
            EmptyLayoutEnum.SHOW_EMPTY -> showEmptyView()
        }

    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }
}