package com.unicorn.vehicle.app.di.module

import com.unicorn.vehicle.data.api.SimpleApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun api(retrofit: Retrofit): SimpleApi = retrofit.create(SimpleApi::class.java)

}