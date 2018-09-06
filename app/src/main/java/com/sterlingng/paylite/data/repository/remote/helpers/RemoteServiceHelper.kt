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

    override fun fundWalletWithCard(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceApi.fundWalletWithCard(data)

    override fun validateOtp(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceApi.validateOtp(data)

    override fun buyAirtime(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceApi.buyAirtime(data)

    override fun fundWalletWithBankAccount(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceApi.fundWalletWithBankAccount(data)

    override fun sendMoney(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceApi.sendMoney(data)

    override fun sendOtp(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceApi.sendOtp(data)

    override fun signin(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceApi.signin(data)

    override fun signup(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceApi.signup(data)

    override fun getWallet(bvn: String): Observable<Response> = mRemoteServiceApi.getWallet(bvn)

    override fun getUser(bvn: String): Observable<Response> = mRemoteServiceApi.getUser(bvn)

    private var mRemoteServiceApi: RemoteServiceApi = createService(RemoteServiceApi::class.java)
}
