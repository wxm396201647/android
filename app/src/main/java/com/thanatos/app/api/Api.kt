package com.thanatos.app.api

import com.thanatos.app.base.BaseBean
import com.thanatos.app.mvp.model.bean.Test
import com.thanatos.app.mvp.model.bean.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("http://gank.io/api/random/data/Android/2")
    fun test(): Call<Test>

    @GET(APIAddressConstants.userLogin)
    fun login(): Call<Response<BaseBean<User>>>

}