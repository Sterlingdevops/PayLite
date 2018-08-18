package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.Log
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter<V : HomeMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), HomeMvpContract<V> {

    override fun loadWallet() {
        val user = dataManager.getCurrentUser()
        Log.d(user.toString())

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.getWallet(user?.token!!, user.username)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            mvpView.hideLoading()
                            mvpView.onGetWalletSuccessful(it)
                        }) {
                            mvpView.hideLoading()
                            mvpView.onGetWalletFailed(it)
                        }
        )
    }
}