package com.sterlingng.paylite.ui.signup.phone

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PhonePresenter<V : PhoneMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), PhoneMvpContract<V> {
    override fun sendOtp(data: HashMap<String, Any>) {
        mvpView.showLoading()
        dataManager.sendOtp(data, AppUtils.gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onSendOTPSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onSendOTPFailed()
                        mvpView.hideLoading()
                    }

                    override fun onAuthorizationError() {

                    }
                })
    }
}