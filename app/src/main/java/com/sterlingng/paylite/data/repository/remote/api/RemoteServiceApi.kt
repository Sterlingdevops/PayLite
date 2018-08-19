package com.sterlingng.paylite.data.repository.remote.api

import com.sterlingng.paylite.data.model.Response
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by rtukpe on 22/03/2018.
 */

interface RemoteServiceApi {
    @POST("users/signup")
    fun signup(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("users/signin")
    fun signin(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("users/{username}")
    fun getUser(@Path("username") username: String): Observable<Response>

    @POST("wallets/fund")
    fun fundWallet(@Header("token") token: String, @Body data: HashMap<String, Any>): Observable<Response>

    @GET("wallets/{username}")
    fun getWallet(@Header("token") token: String, @Path("username") username: String): Observable<Response>

    @POST("wallets/send")
    fun sendMoney(@Header("token") token: String, @Body data: HashMap<String, Any>): Observable<Response>
}