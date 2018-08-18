package com.sterlingng.paylite.data.repository.remote.helpers.base

import com.sterlingng.paylite.utils.AppUtils.gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by rtukpe on 14/03/2018.
 */

open class BaseHelper {
    private val baseUrl: String = "https://paylite-node.herokuapp.com/api/"
    private val okHttpCBuilder = OkHttpClient.Builder()
    private val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))

    protected fun <S> createService(serviceClass: Class<S>): S {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpCBuilder.networkInterceptors().add(httpLoggingInterceptor)
        okHttpCBuilder.retryOnConnectionFailure(true)
        val client: OkHttpClient = okHttpCBuilder.build()

        builder.client(client)
        val retrofit = builder.build()
        return retrofit.create(serviceClass)
    }
}
