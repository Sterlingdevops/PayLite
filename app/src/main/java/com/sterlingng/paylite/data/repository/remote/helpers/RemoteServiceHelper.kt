package com.sterlingng.paylite.data.repository.remote.helpers

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.repository.remote.api.RemoteServiceApi
import com.sterlingng.paylite.data.repository.remote.helpers.base.BaseHelper
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by rtukpe on 22/03/2018.
 */

class RemoteServiceHelper @Inject
internal constructor() : BaseHelper(), RemoteServiceApi {
    override fun getUserTransactions(mobile: String, toDate: String, fromDate: String, authorization: String): Observable<Response> = mRemoteServiceApi.getUserTransactions(mobile, toDate, fromDate, authorization)

    override fun fundWalletWithBankAccount(data: HashMap<String, Any>, authorization: String): Observable<Response> = mRemoteServiceApi.fundWalletWithBankAccount(data, authorization)

    override fun sendOtpForgotPassword(data: HashMap<String, Any>, authorization: String): Observable<Response> = mRemoteServiceApi.sendOtpForgotPassword(data, authorization)

    override fun signin(username: String, password: String, grantType: String): Observable<HashMap<String, Any>> = mRemoteServiceApi.signin(username, password, grantType)

    override fun requestPaymentLink(data: HashMap<String, Any>, authorization: String): Observable<Response> = mRemoteServiceApi.requestPaymentLink(data, authorization)

    override fun fundWalletWithCard(data: HashMap<String, Any>, authorization: String): Observable<Response> = mRemoteServiceApi.fundWalletWithCard(data, authorization)

    override fun buyAirtime(data: HashMap<String, Any>, authorization: String): Observable<Response> = mRemoteServiceApi.buyAirtime(data, authorization)

    override fun sendMoney(data: HashMap<String, Any>, authorization: String): Observable<Response> = mRemoteServiceApi.sendMoney(data, authorization)

    override fun getWallet(mobile: String, authorization: String): Observable<Response> = mRemoteServiceApi.getWallet(mobile, authorization)

    override fun getUser(mobile: String, authorization: String): Observable<Response> = mRemoteServiceApi.getUser(mobile, authorization)

    override fun validateOtp(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceApi.validateOtp(data)

    override fun sendOtp(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceApi.sendOtp(data)

    override fun signup(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceApi.signup(data)

    private var mRemoteServiceApi: RemoteServiceApi = createService(RemoteServiceApi::class.java)
}
