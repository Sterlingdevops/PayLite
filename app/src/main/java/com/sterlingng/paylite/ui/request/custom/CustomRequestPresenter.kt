package com.sterlingng.paylite.ui.request.custom

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CustomRequestPresenter<V : CustomRequestMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), CustomRequestMvpContract<V> {

    override fun requestPaymentLink(data: HashMap<String, Any>) {
        dataManager.getCurrentUser()?.phoneNumber?.let { data["SenderID"] = it }
        mvpView.showLoading()
        dataManager.requestPaymentLink(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onRequestPaymentLinkSent()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onSendRequestPaymentLinkFailed()
                        mvpView.hideLoading()
                    }

                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }
                })
    }
}