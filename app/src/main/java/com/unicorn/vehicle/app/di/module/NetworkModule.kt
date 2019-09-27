package com.unicorn.vehicle.app.di.module

import com.unicorn.vehicle.app.Configs
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
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
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
//            .addInterceptor { chain ->
//                if ("login" in chain.request().url.encodedPathSegments)
//                    chain.proceed(chain.request())
//                else
//                    NetworkHelper.proceedRequestWithSession(chain)
//            }
//            .addInterceptor { chain ->
//                val response = chain.proceed(chain.request())
//                if (response.code != 401) return@addInterceptor response
//                // 401 表示 session 过期
//                NetworkHelper.proceedRequestWithNewSession(chain)
//            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Configs.baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}