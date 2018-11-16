package com.sterlingng.paylite.ui.forgot.token

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TokenPresenter<V : TokenMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), TokenMvpContract<V> {

    override fun validateOtp(data: HashMap<String, Any>) {
        mvpView.showLoading()
        dataManager.validateOtp(data, gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onValidateOtpSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onValidateOtpFailed()
                        mvpView.hideLoading()
                    }
                })
    }
}