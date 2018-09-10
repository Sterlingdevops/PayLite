package com.sterlingng.paylite.data.repository.remote.helpers.base

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.utils.AppUtils.gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by rtukpe on 14/03/2018.
 */

open class BaseHelper {
    private val baseUrl: String = "https://pass.sterlingbankng.com/PayliteApi/"
    private val okHttpCBuilder = OkHttpClient.Builder()
    private val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))

    protected fun <S> createService(serviceClass: Class<S>): S {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        okHttpCBuilder.readTimeout(30, TimeUnit.SECONDS)
        okHttpCBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okHttpCBuilder.networkInterceptors().add(httpLoggingInterceptor)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpCBuilder.retryOnConnectionFailure(true)
        val client: OkHttpClient = okHttpCBuilder.build()

        builder.client(client)
        val retrofit = builder.build()
        retrofit.responseBodyConverter<Response>(Response::class.java, arrayOfNulls<Annotation>(0))
        return retrofit.create(serviceClass)
    }
}
