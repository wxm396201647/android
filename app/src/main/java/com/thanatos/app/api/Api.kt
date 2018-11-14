package com.thanatos.app.api

import com.thanatos.app.base.BaseBean
import com.thanatos.app.base.UserBean
import com.thanatos.app.mvp.model.bean.LoginBean
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST(APIAddressConstants.userLogin)
    fun test(@Body bean: LoginBean): Observable<Response<BaseBean<Void>>>
}