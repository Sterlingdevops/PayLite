package com.sterlingng.paylite.data.repository.remote.helpers

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.repository.remote.api.PayStackServiceApi
import com.sterlingng.paylite.data.repository.remote.helpers.base.PayStackHelper
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by rtukpe on 22/03/2018.
 */

class PayStackServiceHelper @Inject
internal constructor() : PayStackHelper(), PayStackServiceApi {

    override fun getBanks(): Observable<Response> = mPayStackServiceApi.getBanks()

    override fun resolveCardNumber(bin: String): Observable<Response> = mPayStackServiceApi.resolveCardNumber(bin)

    override fun resolveBankAccount(accountNumber: String, bankCode: String): Observable<Response> = mPayStackServiceApi.resolveBankAccount(accountNumber, bankCode)

    private var mPayStackServiceApi: PayStackServiceApi = createService(PayStackServiceApi::class.java)
}
