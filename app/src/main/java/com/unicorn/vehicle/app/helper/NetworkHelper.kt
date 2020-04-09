package com.unicorn.vehicle.app.helper

import com.blankj.utilcode.util.EncryptUtils
import com.unicorn.vehicle.app.*
import com.unicorn.vehicle.app.di.Holder
import com.unicorn.vehicle.data.model.UserLoginParam
import okhttp3.Interceptor
import okhttp3.Response

object NetworkHelper {

    fun proceedRequestWithNewSession(chain: Interceptor.Chain): Response = with(AppInfo) {
        api.autoLogin(
            UserLoginParam(
                loginStr = LoginStr,
                userPwd = EncryptUtils.encryptMD5ToString(UserPwd)
            )
        ).execute().body().let { loggedUser = it!!.data }
        return proceedRequestWithSession(chain)
    }

    fun proceedRequestWithSession(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder()
            .removeHeader(Cookie)
            .addHeader(Cookie, "${SESSION}=${sid}")
            .build()
            .let { chain.proceed(it) }
    }

    private val api by lazy { Holder.appComponent.simpleApi() }

}