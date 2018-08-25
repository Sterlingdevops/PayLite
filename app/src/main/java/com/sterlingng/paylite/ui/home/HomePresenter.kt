package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter<V : HomeMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), HomeMvpContract<V> {

    override fun loadCachedWallet() {
        mvpView.onGetWalletSuccessful(dataManager.getWallet()!!)
    }

    override fun onViewInitialized() {
        super.onViewInitialized()
        mvpView.onGetWalletSuccessful(dataManager.getWallet()!!)
        mvpView.initView(dataManager.getCurrentUser())
    }

    override fun loadWallet() {
        val user = dataManager.getCurrentUser()
        compositeDisposable.add(
                dataManager.getWallet(user?.token!!, user.username)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            val wallet = gson.fromJson(gson.toJson(it.data), Wallet::class.java)
                            dataManager.saveWallet(wallet)
                            mvpView.onGetWalletSuccessful(wallet)
                        }) {
                            mvpView.onGetWalletFailed(it)
                        }
        )
    }
}