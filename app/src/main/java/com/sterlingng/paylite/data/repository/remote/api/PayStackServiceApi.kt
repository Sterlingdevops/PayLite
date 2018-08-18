package com.sterlingng.paylite.data.repository.remote.api

import com.sterlingng.paylite.data.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PayStackServiceApi {

    @GET("bank")
    @Headers("Authorization: Bearer sk_test_4844e2650b69fd92f0af204275ca74b9f1d1336f")
    fun getBanks(): Observable<Response>

    @GET("bank/resolve")
    @Headers("Authorization: Bearer sk_test_4844e2650b69fd92f0af204275ca74b9f1d1336f")
    fun resolveBankAccount(@Query("account_number") accountNumber: String,
                           @Query("bank_code") bankCode: String): Observable<Response>

    @GET("decision/bin/{bin}")
    @Headers("Authorization: Bearer sk_test_4844e2650b69fd92f0af204275ca74b9f1d1336f")
    fun resolveCardNumber(@Path("bin") bin: String): Observable<Response>
}