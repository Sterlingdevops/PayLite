package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter<V : HomeMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), HomeMvpContract<V> {

    override fun getAuthPin(): Boolean {
        dataManager.getPin(dataManager.getCurrentUser()?.phoneNumber!!)?.let { return true }
        return false
    }

    override fun onViewInitialized() {
        super.onViewInitialized()
        mvpView.initView(dataManager.getCurrentUser(), dataManager.mockMenuItems())
    }

    override fun loadCachedWallet() {
        dataManager.getWallet()?.let {
            mvpView.onGetWalletSuccessful(it)
        }
    }

    override fun loadWallet() {
        val user = dataManager.getCurrentUser()
        dataManager.getWallet(user?.phoneNumber!!, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}")
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        val wallet = AppUtils.gson.fromJson(AppUtils.gson.toJson(response.data), Wallet::class.java)
                        dataManager.saveWallet(wallet)
                        mvpView.onGetWalletSuccessful(wallet)
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onGetWalletFailed()
                    }
                })
    }
}