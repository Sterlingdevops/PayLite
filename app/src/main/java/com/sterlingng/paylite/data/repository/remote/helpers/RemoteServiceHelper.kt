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

    override fun cashoutToBankAccount(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceApi.cashoutToBankAccount(data, authorization, hash)

    override fun bankNameEnquiry(accountNumber: String, bankCode: String, authorization: String): Observable<Response> = mRemoteServiceApi.bankNameEnquiry(accountNumber, bankCode, authorization)

    override fun signIn(username: String, password: String, initializationVector: String, grantType: String): Observable<HashMap<String, Any>> = mRemoteServiceApi.signIn(username, password, initializationVector, grantType)

    override fun getUserTransactions(mobile: String, toDate: String, fromDate: String, authorization: String): Observable<Response> = mRemoteServiceApi.getUserTransactions(mobile, toDate, fromDate, authorization)

    override fun fundWalletWithBankAccount(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceApi.fundWalletWithBankAccount(data, authorization, hash)

    override fun sendOtpForgotPassword(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceApi.sendOtpForgotPassword(data, authorization, hash)

    override fun requestPaymentLink(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceApi.requestPaymentLink(data, authorization, hash)

    override fun fundWalletWithCard(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceApi.fundWalletWithCard(data, authorization, hash)

    override fun splitPayment(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceApi.splitPayment(data, authorization, hash)

    override fun buyAirtime(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceApi.buyAirtime(data, authorization, hash)

    override fun sendMoney(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceApi.sendMoney(data, authorization, hash)

    override fun getWallet(mobile: String, authorization: String): Observable<Response> = mRemoteServiceApi.getWallet(mobile, authorization)

    override fun validateOtp(data: HashMap<String, Any>, hash: String): Observable<Response> = mRemoteServiceApi.validateOtp(data, hash)

    override fun getUser(mobile: String, authorization: String): Observable<Response> = mRemoteServiceApi.getUser(mobile, authorization)

    override fun sendOtp(data: HashMap<String, Any>, hash: String): Observable<Response> = mRemoteServiceApi.sendOtp(data, hash)

    override fun signup(data: HashMap<String, Any>, hash: String): Observable<Response> = mRemoteServiceApi.signup(data, hash)

    override fun getBanks(authorization: String): Observable<Response> = mRemoteServiceApi.getBanks(authorization)

    private var mRemoteServiceApi: RemoteServiceApi = createService(RemoteServiceApi::class.java)
}
