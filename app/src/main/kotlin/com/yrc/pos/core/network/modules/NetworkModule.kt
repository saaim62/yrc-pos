package com.yrc.pos.core.network.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yrc.pos.core.prefs.YrcPrefManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



val retrofitModule = module {

    fun getGson(): Gson {
        return GsonBuilder().setLenient().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun getHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return OkHttpClient.Builder().addInterceptor(logging)
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.MINUTES).build()
    }

    fun getRetrofit(factory: Gson, client: OkHttpClient, prefManager: YrcPrefManager): Retrofit {
        return Retrofit.Builder()
                .baseUrl(prefManager.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(factory))
                .client(client)
                .build()
    }

    single { getGson() }
    single { getHttpClient() }
    single { getRetrofit(get(), get(), get()) }
}