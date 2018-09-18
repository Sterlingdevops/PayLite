package com.sterlingng.paylite.ui.request.custom

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class CustomRequestPresenter<V : CustomRequestMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), CustomRequestMvpContract<V> {

    override fun requestPaymentLink(data: HashMap<String, Any>) {
        dataManager.getCurrentUser()?.firstName?.let { data["username"] = it }
        dataManager.getCurrentUser()?.phoneNumber?.let { data["phone"] = it }

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.requestPaymentLink(data,
                        "Bearer ${dataManager.getCurrentUser()?.accessToken!!}",
                        AppUtils.gson.toJson(data).sha256())
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .onErrorReturn {
                            if (it is SocketTimeoutException) {
                                val response = Response()
                                response.data = SocketTimeoutException()
                                response.message = "Error!!! The server didn't respond fast enough and the request timed out"
                                response.response = "failed"
                                return@onErrorReturn response
                            } else {
                                val raw = (it as HttpException).response().errorBody()?.string()
                                if (AppUtils.isJSONValid(raw!!)) {
                                    return@onErrorReturn AppUtils.gson.fromJson(raw, Response::class.java)
                                }
                                val response = Response()
                                response.data = HttpException(retrofit2.Response.error<String>(500,
                                        ResponseBody.create(MediaType.parse("text/html; charset=utf-8"), raw)))
                                response.message = "Error!!! The server didn't respond fast enough and the request timed out"
                                response.response = "failed"
                                return@onErrorReturn response
                            }
                        }
                        .subscribe {
                            if (it.response != null && it.response == "00") {
                                mvpView.onRequestPaymentLinkSent(it)
                            } else {
                                if (it.message == "Authorization has been denied for this request") {
                                    mvpView.logout()
                                } else {
                                    mvpView.onSendRequestPaymentLinkFailed(it)
                                }
                            }
                            mvpView.hideLoading()
                        }
        )
    }
}