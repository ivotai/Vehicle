package com.unicorn.vehicle.app.helper

import com.blankj.utilcode.util.EncryptUtils
import com.github.florent37.rxsharedpreferences.RxSharedPreferences
import com.unicorn.vehicle.app.Globals
import com.unicorn.vehicle.app.Key
import com.unicorn.vehicle.app.di.ComponentHolder
import com.unicorn.vehicle.data.model.UserLoginParam
import okhttp3.Interceptor
import okhttp3.Response

object NetworkHelper {

    fun proceedRequestWithNewSession(chain: Interceptor.Chain): Response {
        var loginStr = ""
        var userPwd = ""
        RxSharedPreferences.with(context).apply {
            getString(Key.LoginStr, "").subscribe { loginStr = it }
            getString(Key.UserPwd, "").subscribe { userPwd = it }
        }
        api.autoLogin(UserLoginParam(loginStr = loginStr, userPwd =  EncryptUtils.encryptMD5ToString(userPwd))).execute().body()
            .let { Globals.loggedUser = it!!.data }
        return proceedRequestWithSession(chain)
    }

    fun proceedRequestWithSession(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder()
            .removeHeader(Key.Cookie)
            .addHeader(Key.Cookie, "${Key.SESSION}=${Globals.sid}")
            .build()
            .let { chain.proceed(it) }
    }

    private val api by lazy { ComponentHolder.appComponent.api() }

    private val context by lazy { ComponentHolder.appComponent.context() }

}