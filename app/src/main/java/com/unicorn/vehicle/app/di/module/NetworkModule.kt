package com.unicorn.vehicle.app.di.module

import com.google.gson.Gson
import com.unicorn.vehicle.app.baseUrl
import com.unicorn.vehicle.app.helper.NetworkHelper
import com.unicorn.vehicle.data.model.base.ErrorCode
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(gson: Gson): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val encodedPathSegments = chain.request().url.encodedPathSegments
                if ("Code" in encodedPathSegments || "UserLogin" in encodedPathSegments)
                    chain.proceed(chain.request())
                else
                    NetworkHelper.proceedRequestWithSession(chain)
            }
            .addInterceptor { chain ->
                // 非空检测
                val response = chain.proceed(chain.request())
                val body = response.body ?: return@addInterceptor response
                // 判断是否登录超时
                val content = body.string()
                val errorCode = gson.fromJson(content, ErrorCode::class.java)
                // 如果没有登录超时，返回新的 response
                if (errorCode?.errorCode != "1000") {
                    return@addInterceptor response.newBuilder()
                        .body(content.toResponseBody(body.contentType()))
                        .build()
                }
                // 如果登录超时，自动登录
                NetworkHelper.proceedRequestWithNewSession(chain)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}