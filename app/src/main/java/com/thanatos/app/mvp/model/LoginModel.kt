package com.thanatos.app.mvp.model

import com.thanatos.app.api.APIManager
import com.thanatos.app.base.BaseBean
import com.thanatos.app.mvp.model.bean.Test
import com.thanatos.app.mvp.model.bean.User
import retrofit2.Call
import retrofit2.Response

class LoginModel {
    /**
     * 登录
     */
    fun test(): Call<Test> {
        return APIManager.api.test()
    }

    fun login(): Call<Response<BaseBean<User>>> {
        return APIManager.api.login()
    }
}