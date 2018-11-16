package com.sterlingng.paylite.ui.airtime

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AirTimePresenter<V : AirTimeMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), AirTimeMvpContract<V> {

    override fun buyAirtime(data: HashMap<String, Any>) {
        dataManager.getCurrentUser()?.phoneNumber?.let { data["UserAcct"] = it }
        mvpView.showLoading()
        dataManager
                .buyAirtime(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onBuyAirtimeSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onBuyAirtimeFailed()
                        mvpView.hideLoading()
                    }
                })
    }

    override fun loadCachedWallet() {
        dataManager.getWallet()?.let { mvpView.initView(it) }
    }
}