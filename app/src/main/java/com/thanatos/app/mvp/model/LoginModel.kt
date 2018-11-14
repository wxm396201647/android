package com.thanatos.app.mvp.model

import com.thanatos.app.api.scheduler.SchedulerUtils
import com.thanatos.app.api.APIManager
import com.thanatos.app.base.BaseBean
import com.thanatos.app.mvp.model.bean.LoginBean
import io.reactivex.Observable
import retrofit2.Response

class LoginModel {
    /**
     * 登录
     */
    fun login(bean: LoginBean): Observable<Response<BaseBean<Void>>> {
        return APIManager.api.test(bean).compose(SchedulerUtils.ioToMain())
    }
}