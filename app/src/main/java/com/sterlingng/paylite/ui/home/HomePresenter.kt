package com.sterlingng.paylite.ui.home

import com.google.gson.internal.LinkedTreeMap
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.Log
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter<V : HomeMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), HomeMvpContract<V> {

    override fun loadWallet() {
        val user = dataManager.getCurrentUser()

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.getWallet(user?.token!!, user.username)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            val wallet = gson.fromJson(gson.toJson((it.data as LinkedTreeMap<String, Any>)["wallet"]), Wallet::class.java)

                            dataManager.saveWallet(wallet)
                            Log.d(wallet.toString())

                            mvpView.hideLoading()
                            mvpView.onGetWalletSuccessful(wallet)
                        }) {
                            mvpView.hideLoading()
                            mvpView.onGetWalletFailed(it)
                        }
        )
    }
}