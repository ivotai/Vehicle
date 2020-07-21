package com.unicorn.vehicle.app.di.module

import com.unicorn.vehicle.app.V1
import com.unicorn.vehicle.app.V2
import com.unicorn.vehicle.data.api.SimpleApi
import com.unicorn.vehicle.data.api.UpdateApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class ApiModule {

    @Provides
    fun provideSimpleApi(@Named(V1) retrofit: Retrofit): SimpleApi =
        retrofit.create(SimpleApi::class.java)

    @Provides
    fun provideUpdateApi(@Named(V2) retrofit: Retrofit): UpdateApi =
        retrofit.create(UpdateApi::class.java)

}