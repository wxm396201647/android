package com.thanatos.app.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import com.beltaief.flowlayout.FlowLayout
import com.blankj.utilcode.util.KeyboardUtils
import com.thanatos.app.utils.EmptyLayoutEnum
import com.thanatos.app.utils.StatusBarUtil
import com.thanatos.app.view.CustomProgressDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


abstract class BaseActivity<T : IBaseView, K : BasePresenter<T>> : AppCompatActivity(), IBaseView, EasyPermissions.PermissionCallbacks, CoroutineScope by MainScope() {
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

    fun statusBarDark() {
        StatusBarUtil.darkMode(this)
    }

    fun paddingStatusBar(view: View) {
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
        progressDialog.show()
    }

    /**
     * 进度弹窗消失
     */
    override fun dismissLoading() {
        progressDialog.dismiss()
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
        progressDialog.dismiss()
        cancel()
        if (isRegistEventBus()) EventBus.getDefault().unregister(this)
        // 收起输入框
        KeyboardUtils.hideSoftInput(this)
    }


    /**
     * 根据EmptyLayoutEnum 显示EmptyLayout
     */
    open fun setEmptyByType(enumType: EmptyLayoutEnum) {
        when (enumType) {
            EmptyLayoutEnum.SHOW_CONTENT -> showCommonView()
            EmptyLayoutEnum.SHOW_NO_NET -> showNoNetView()
            EmptyLayoutEnum.SHOW_ERROR -> showErrorView()
            EmptyLayoutEnum.SHOW_EMPTY -> showEmptyView()
        }
    }

    /**
     * 重写要申请权限的Activity或者Fragment的onRequestPermissionsResult()方法，
     * 在里面调用EasyPermissions.onRequestPermissionsResult()，实现回调。
     *
     * @param requestCode  权限请求的识别码
     * @param permissions  申请的权限
     * @param grantResults 授权结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 当权限被成功申请的时候执行回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.i("EasyPermissions", "获取成功的权限$perms")
    }

    /**
     * 当权限申请失败的时候执行的回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        //处理权限名字字符串
        val sb = StringBuffer()
        for (str in perms) {
            sb.append(str)
            sb.append("\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        //用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(this, "已拒绝权限" + sb + "并不再询问", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                    .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("好")
                    .setNegativeButton("不行")
                    .build()
                    .show()
        }
    }
}