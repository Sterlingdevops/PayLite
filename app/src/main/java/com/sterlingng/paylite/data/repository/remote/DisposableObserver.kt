package com.sterlingng.paylite.data.repository.remote

import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.utils.AppConstants
import com.sterlingng.paylite.utils.Log
import io.reactivex.observers.DisposableObserver
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

abstract class DisposableObserver : DisposableObserver<Response>() {

    abstract fun onRequestSuccessful(response: Response, message: String)

    abstract fun onRequestFailed(code: Int, failureReason: String)

    abstract fun onAuthorizationError()

    override fun onComplete() {

    }

    override fun onNext(t: Response) {
        Log.d("Request Response ----> " + t.toString())
        val message = t.message?.let {
            it
        } ?: "The server didn't return a valid response"
        if (t.code == 401) onAuthorizationError()
        if (t.data == null || t.data is String || (t.data is List<*> && (t.data as List<*>).isEmpty())) {
            onRequestFailed(t.code, message)
            return
        }
        if (t.status || t.response != null && t.response == "00") {
            onRequestSuccessful(t, message)
        }
    }

    override fun onError(e: Throwable) {
//        Crashlytics.logException(e)
        if (e is ConnectException) {
            onRequestFailed(400, AppConstants.CONNECT_EXCEPTION)
            return
        }
        if (e is UnknownHostException) {
            onRequestFailed(400, AppConstants.UNKNOWN_HOST_EXCEPTION)
            return
        }
        if (e is SocketTimeoutException) {
            onRequestFailed(400, AppConstants.SOCKET_TIME_OUT_EXCEPTION)
            return
        }
        if (e is HttpException) {
            try {
                val response = e.response()
                val json = JSONObject(response.errorBody()?.string())
                val errorMessage = json.getString("message")
                onRequestFailed(400, errorMessage)
            } catch (err: JSONException) {
                Log.e("JSON Exception", err.localizedMessage)
//                Crashlytics.logException(err)
                onRequestFailed(400, AppConstants.UNKNOWN_NETWORK_EXCEPTION)
            } catch (err: IOException) {
                Log.e("Network IO Exception", err.localizedMessage)
//                Crashlytics.logException(err)
                onRequestFailed(400, AppConstants.UNKNOWN_NETWORK_EXCEPTION)
            } catch (err: SSLException) {
                Log.e("Network IO Exception", err.localizedMessage)
//                Crashlytics.logException(err)
                onRequestFailed(400, AppConstants.SSL_EXCEPTION)
            }
            return
        }
        onRequestFailed(400, AppConstants.UNKNOWN_NETWORK_EXCEPTION)
    }
}