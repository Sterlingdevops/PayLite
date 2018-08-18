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
    override fun signup(data: HashMap<String, Any>): Observable<Response> {
        return mRemoteServiceApi.signup(data)
    }

    override fun signin(data: HashMap<String, Any>): Observable<Response> {
        return mRemoteServiceApi.signin(data)
    }

    override fun getUser(username: String): Observable<Response> {
        return mRemoteServiceApi.getUser(username)
    }

    override fun fundWallet(token: String, data: HashMap<String, Any>): Observable<Response> {
        return mRemoteServiceApi.fundWallet(token, data)
    }

    override fun getWallet(token: String, username: String): Observable<Response> {
        return mRemoteServiceApi.getWallet(token, username)
    }

    private var mRemoteServiceApi: RemoteServiceApi = createService(RemoteServiceApi::class.java)
}
