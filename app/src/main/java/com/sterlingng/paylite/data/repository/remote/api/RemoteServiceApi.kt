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

    // GET

    @GET("GetUserDetails")
    fun getUser(@Query("bvn") bvn: String): Observable<Response>

    @GET("GetFullWalletDetails")
    fun getWallet(@Query("bvn") bvn: String): Observable<Response>

    @GET("GetUserTransactions")
    fun getUserTransactions(@Query("bvn.bvn") bvn: String,
                            @Query("bvn.toDate") toDate: String,
                            @Query("bvn.fromDate") fromDate: String): Observable<Response>

    // POST

    @POST("LoginUser")
    fun signin(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("RegisterUser")
    fun signup(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("SendOtp")
    fun sendOtp(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("SendForgotPasswordTokenForMail")
    fun sendOtpForgotPassword(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("SendMoneyViaUserHandle")
    fun sendMoney(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("SterlingAccountToWallet")
    fun fundWalletWithBankAccount(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("DebitAnyBankCard")
    fun fundWalletWithCard(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("ValidateOtp")
    fun validateOtp(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("RequestPaymentLink")
    fun requestPaymentLink(@Body data: HashMap<String, Any>): Observable<Response>

    @POST("BuyAirtimeFromWallet")
    fun buyAirtime(@Body data: HashMap<String, Any>): Observable<Response>
}