package com.sterlingng.paylite.data.repository.remote.api

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.utils.CLIENT_ID
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by rtukpe on 22/03/2018.
 */

interface RemoteServiceApi {

    // GET

    @GET("api/Paylite/GetUserDetails")
    @Headers("X-CID: $CLIENT_ID")
    fun getUser(@Query("mobile") mobile: String,
                @Header("Authorization") authorization: String): Observable<Response>

    @GET("api/Paylite/BankNameEnquiry")
    @Headers("X-CID: $CLIENT_ID")
    fun bankNameEnquiry(@Query("accountNumber") accountNumber: String,
                        @Query("destinationBankCode") bankCode: String,
                        @Header("Authorization") authorization: String): Observable<Response>

    @GET("api/Paylite/GetFullWalletDetails")
    @Headers("X-CID: $CLIENT_ID")
    fun getWallet(@Query("mobile") mobile: String,
                  @Header("Authorization") authorization: String): Observable<Response>

    @GET("api/Paylite/GetSchedulePayments")
    @Headers("X-CID: $CLIENT_ID")
    fun getSchedulePayments(@Query("UserMobile") mobile: String,
                            @Header("Authorization") authorization: String): Observable<Response>

    @GET("api/Paylite/GetAllBanks")
    @Headers("X-CID: $CLIENT_ID")
    fun getBanks(@Header("Authorization") authorization: String): Observable<Response>

    @GET("api/Paylite/GetUserRelativeTransactions")
    @Headers("X-CID: $CLIENT_ID")
    fun getUserRelativeTransactions(@Query("primaryAcct") primaryAcct: String,
                                    @Query("secondaryAcct") secondaryAcct: String,
                                    @Header("Authorization") authorization: String): Observable<Response>

    @GET("api/Paylite/GetUserTransactions")
    @Headers("X-CID: $CLIENT_ID")
    fun getUserTransactions(@Query("mobile") mobile: String,
                            @Query("toDate") toDate: String,
                            @Query("fromDate") fromDate: String,
                            @Header("Authorization") authorization: String): Observable<Response>

    // DELETE

    @DELETE("api/Paylite/DeleteSchedulePayments")
    @Headers("X-CID: $CLIENT_ID")
    fun deleteSchedulePayments(@Query("UserMobile") mobile: String,
                               @Query("Paymentrefid") reference: String,
                               @Header("Authorization") authorization: String): Observable<Response>

    // POST

    @FormUrlEncoded
    @POST("AccessToken")
    fun signIn(@Field("username") username: String,
               @Field("password") password: String,
               @Header("X-IV") initializationVector: String,
               @Field("grant_type") grantType: String): Observable<HashMap<String, Any>>

    @POST("api/Paylite/RegisterUsers")
    @Headers("X-CID: $CLIENT_ID")
    fun signup(@Body data: HashMap<String, Any>,
               @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/SendOtp")
    @Headers("X-CID: $CLIENT_ID")
    fun sendOtp(@Body data: HashMap<String, Any>,
                @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/SplitPayment")
    @Headers("X-CID: $CLIENT_ID")
    fun splitPayment(@Body data: HashMap<String, Any>,
                     @Header("Authorization") authorization: String,
                     @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/SendForgotPasswordTokenForMail")
    @Headers("X-CID: $CLIENT_ID")
    fun sendOtpForgotPassword(@Body data: HashMap<String, Any>,
                              @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/SendMoneyViaUserHandle")
    @Headers("X-CID: $CLIENT_ID")
    fun sendMoney(@Body data: HashMap<String, Any>,
                  @Header("Authorization") authorization: String,
                  @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/SterlingAccountToWallet")
    @Headers("X-CID: $CLIENT_ID")
    fun fundWalletWithBankAccount(@Body data: HashMap<String, Any>,
                                  @Header("Authorization") authorization: String,
                                  @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/DebitAnyBankCard")
    @Headers("X-CID: $CLIENT_ID")
    fun fundWalletWithCard(@Body data: HashMap<String, Any>,
                           @Header("Authorization") authorization: String,
                           @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/ValidateOtp")
    @Headers("X-CID: $CLIENT_ID")
    fun validateOtp(@Body data: HashMap<String, Any>,
                    @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/RequestPaymentLink")
    @Headers("X-CID: $CLIENT_ID")
    fun requestPaymentLink(@Body data: HashMap<String, Any>,
                           @Header("Authorization") authorization: String,
                           @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/BuyAirtimeFromWallet")
    @Headers("X-CID: $CLIENT_ID")
    fun buyAirtimeFromWallet(@Body data: HashMap<String, Any>,
                             @Header("Authorization") authorization: String,
                             @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/SchedulePayments")
    @Headers("X-CID: $CLIENT_ID")
    fun schedulePayments(@Body data: HashMap<String, Any>,
                         @Header("Authorization") authorization: String,
                         @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/BuyAirtimeFromWallet")
    @Headers("X-CID: $CLIENT_ID")
    fun buyAirtime(@Body data: HashMap<String, Any>,
                   @Header("Authorization") authorization: String,
                   @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/CashoutToBankAccount")
    @Headers("X-CID: $CLIENT_ID")
    fun cashoutToBankAccount(@Body data: HashMap<String, Any>,
                             @Header("Authorization") authorization: String,
                             @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/CashoutToSterlingBankAccount")
    @Headers("X-CID: $CLIENT_ID")
    fun cashoutToSterlingBankAccount(@Body data: HashMap<String, Any>,
                                     @Header("Authorization") authorization: String,
                                     @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/CashOutViaPayCode")
    @Headers("X-CID: $CLIENT_ID")
    fun cashOutViaPayCode(@Body data: HashMap<String, Any>,
                          @Header("Authorization") authorization: String,
                          @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/CashOutViaPayCode")
    @Headers("X-CID: $CLIENT_ID")
    fun cashOutViaBranch(@Body data: HashMap<String, Any>,
                         @Header("Authorization") authorization: String,
                         @Header("X-CS") hash: String): Observable<Response>

    @POST("api/Paylite/UpdateUserDetails")
    @Headers("X-CID: $CLIENT_ID")
    fun updateUserDetails(@Body data: HashMap<String, Any>,
                          @Header("Authorization") authorization: String,
                          @Header("X-CS") hash: String): Observable<Response>

    // PUT

    @PUT("api/Paylite/UpdateForgotPassword")
    @Headers("X-CID: $CLIENT_ID")
    fun updateForgotPassword(@Body data: HashMap<String, Any>,
                             @Header("X-CS") hash: String): Observable<Response>
}