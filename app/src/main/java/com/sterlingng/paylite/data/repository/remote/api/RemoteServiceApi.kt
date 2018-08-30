package com.sterlingng.paylite.data.repository.remote.api

import com.sterlingng.paylite.data.model.Response
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by rtukpe on 22/03/2018.
 */

interface RemoteServiceApi {
    @POST("LoginUser")
    fun signin(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("RegisterUser")
    fun signup(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("SendOtp")
    fun sendOtp(@Body data: HashMap<String, Any>): Observable<Response>

    @GET("GetUserDetails")
    fun getUser(@Query("bvn") bvn: String): Observable<Response>

    @POST("SendMoneyViaUserHandle")
    fun sendMoney(@Body data: HashMap<String, Any>): Observable<Response>

    @GET("GetFullWalletDetails")
    fun getWallet(@Query("bvn") bvn: String): Observable<Response>

    @POST("wallets/fund")
    fun fundWallet(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("ValidateOtp")
    fun validateOtp(@Body data: HashMap<String, Any>): Observable<Response>
}