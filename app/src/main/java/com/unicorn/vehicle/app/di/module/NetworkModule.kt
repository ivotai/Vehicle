package com.unicorn.vehicle.app.di.module

import com.google.gson.Gson
import com.unicorn.vehicle.app.V1
import com.unicorn.vehicle.app.V2
import com.unicorn.vehicle.app.baseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rxhttp.wrapper.param.RxHttp
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(gson: Gson): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                chain.request().newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", "SmartKeyManagementApp")
                    .build()
                    .let { chain.proceed(it) }
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        return builder.build()
    }

    @Singleton
    @Provides
    @Named(V1)
    fun provideRetrofitV1(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    @Singleton
    @Provides
    @Named(V2)
    fun provideRetrofitV2(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
//        http://115.28.211.99:8080/api/file/CheckAppVersion
        return Retrofit.Builder()
            .baseUrl("http://115.28.211.99:8080/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}