package com.sterlingng.paylite.ui.getcash

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GetCashPresenter<V : GetCashMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), GetCashMvpContract<V> {

    override fun onViewInitialized() {
        super.onViewInitialized()
        dataManager.getWallet()?.let { mvpView.initView(it) }
    }

    override fun cashOutViaPayCode(data: HashMap<String, Any>, self: Boolean) {
        val user = dataManager.getCurrentUser()
        data["UserAcct"] = user?.phoneNumber!!
        data["CustomerEmail"] = user.email

        if (self) data["mobile"] = user.phoneNumber

        mvpView.showLoading()
        dataManager.cashOutViaPayCode(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onCashOutSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onCashOutFailed()
                        mvpView.hideLoading()
                    }

                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }
                })
    }
}