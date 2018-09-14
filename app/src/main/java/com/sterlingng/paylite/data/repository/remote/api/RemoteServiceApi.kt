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
    @Headers("X-CID: 42aec90f-0142-48de-a66b-e637596fc7b8")
    fun getUser(@Query("mobile") mobile: String, @Header("Authorization") authorization: String): Observable<Response>

    @GET("api/Paylite/GetFullWalletDetails")
    @Headers("X-CID: 42aec90f-0142-48de-a66b-e637596fc7b8")
    fun getWallet(@Query("mobile") mobile: String, @Header("Authorization") authorization: String): Observable<Response>

    @GET("api/Paylite/GetUserTransactions")
    @Headers("X-CID: 42aec90f-0142-48de-a66b-e637596fc7b8")
    fun getUserTransactions(@Query("mobile") mobile: String,
                            @Query("toDate") toDate: String,
                            @Query("fromDate") fromDate: String,
                            @Header("Authorization") authorization: String): Observable<Response>

    // POST

    @FormUrlEncoded
    @POST("AccessToken")
    fun signIn(@Field("username") username: String,
               @Field("password") password: String,
               @Header("X-IV") initializationVector: String,
               @Field("grant_type") grantType: String): Observable<HashMap<String, Any>>

    @POST("api/Paylite/RegisterUser")
    @Headers("X-CID: 42aec90f-0142-48de-a66b-e637596fc7b8")
    fun signup(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("api/Paylite/SendOtp")
    @Headers("X-CID: 42aec90f-0142-48de-a66b-e637596fc7b8")
    fun sendOtp(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("api/Paylite/SendForgotPasswordTokenForMail")
    @Headers("X-CID: 42aec90f-0142-48de-a66b-e637596fc7b8")
    fun sendOtpForgotPassword(@Body data: HashMap<String, Any>, @Header("Authorization") authorization: String, @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/SendMoneyViaUserHandle")
    @Headers("X-CID: 42aec90f-0142-48de-a66b-e637596fc7b8")
    fun sendMoney(@Body data: HashMap<String, Any>, @Header("Authorization") authorization: String, @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/SterlingAccountToWallet")
    @Headers("X-CID: 42aec90f-0142-48de-a66b-e637596fc7b8")
    fun fundWalletWithBankAccount(@Body data: HashMap<String, Any>, @Header("Authorization") authorization: String, @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/DebitAnyBankCard")
    @Headers("X-CID: 42aec90f-0142-48de-a66b-e637596fc7b8")
    fun fundWalletWithCard(@Body data: HashMap<String, Any>, @Header("Authorization") authorization: String, @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/ValidateOtp")
    @Headers("X-CID: 42aec90f-0142-48de-a66b-e637596fc7b8")
    fun validateOtp(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("api/Paylite/RequestPaymentLink")
    @Headers("X-CID: 42aec90f-0142-48de-a66b-e637596fc7b8")
    fun requestPaymentLink(@Body data: HashMap<String, Any>, @Header("Authorization") authorization: String, @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/BuyAirtimeFromWallet")
    @Headers("X-CID: 42aec90f-0142-48de-a66b-e637596fc7b8")
    fun buyAirtime(@Body data: HashMap<String, Any>, @Header("Authorization") authorization: String, @Header("X-CS") hash: String): Observable<Response>
}