package com.yrc.pos.core.network.modules

import android.content.Context
import com.yrc.pos.core.services.ApiInterface
import com.yrc.pos.core.services.YrcAPIManger
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun getApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    fun getYrcAPIManger(apiInterface: ApiInterface, context: Context): YrcAPIManger {
        return YrcAPIManger(apiInterface, context)
    }

    single { getApiInterface(get()) }

    single { getYrcAPIManger(get(), get())}
}