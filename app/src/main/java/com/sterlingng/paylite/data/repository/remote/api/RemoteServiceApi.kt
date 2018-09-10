package com.sterlingng.paylite.data.repository.remote.api

import com.sterlingng.paylite.data.model.Response
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by rtukpe on 22/03/2018.
 */

interface RemoteServiceApi {

    // GET

    @GET("api/Paylite/GetUserDetails")
    fun getUser(@Query("mobile") mobile: String, @Header("Authorization") authorization: String): Observable<Response>

    @GET("api/Paylite/GetFullWalletDetails")
    fun getWallet(@Query("mobile") mobile: String, @Header("Authorization") authorization: String): Observable<Response>

    @GET("api/Paylite/GetUserTransactions")
    fun getUserTransactions(@Query("mobile") mobile: String,
                            @Query("toDate") toDate: String,
                            @Query("fromDate") fromDate: String,
                            @Header("Authorization") authorization: String): Observable<Response>

    // POST

    @FormUrlEncoded
    @POST("AccessToken")
    fun signin(@Field("username") username: String,
               @Field("password") password: String,
               @Field("grant_type") grantType: String): Observable<HashMap<String, Any>>

    @POST("api/Paylite/RegisterUser")
    fun signup(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("api/Paylite/SendOtp")
    fun sendOtp(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("api/Paylite/SendForgotPasswordTokenForMail")
    fun sendOtpForgotPassword(@Body data: HashMap<String, Any>, @Header("Authorization") authorization: String): Observable<Response>

    @POST("api/Paylite/SendMoneyViaUserHandle")
    fun sendMoney(@Body data: HashMap<String, Any>, @Header("Authorization") authorization: String): Observable<Response>

    @POST("api/Paylite/SterlingAccountToWallet")
    fun fundWalletWithBankAccount(@Body data: HashMap<String, Any>, @Header("Authorization") authorization: String): Observable<Response>

    @POST("api/Paylite/DebitAnyBankCard")
    fun fundWalletWithCard(@Body data: HashMap<String, Any>, @Header("Authorization") authorization: String): Observable<Response>

    @POST("api/Paylite/ValidateOtp")
    fun validateOtp(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("api/Paylite/RequestPaymentLink")
    fun requestPaymentLink(@Body data: HashMap<String, Any>, @Header("Authorization") authorization: String): Observable<Response>

    @POST("api/Paylite/BuyAirtimeFromWallet")
    fun buyAirtime(@Body data: HashMap<String, Any>, @Header("Authorization") authorization: String): Observable<Response>
}