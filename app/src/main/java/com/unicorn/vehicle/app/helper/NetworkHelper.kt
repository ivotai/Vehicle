package com.unicorn.vehicle.app.helper

import com.unicorn.vehicle.app.Globals
import com.unicorn.vehicle.app.Key
import com.unicorn.vehicle.app.di.ComponentHolder
import okhttp3.Interceptor
import okhttp3.Response

object NetworkHelper {

    fun proceedRequestWithNewSession(chain: Interceptor.Chain): Response {
        api.autoLogin(Globals.userLoginParam).execute().body()
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

}