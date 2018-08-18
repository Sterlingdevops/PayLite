package com.sterlingng.paylite.ui.fund

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.Log
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FundPresenter<V : FundMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), FundMvpContract<V> {


    override fun resolveAccountNumber(accountNumber: String, bankCode: String) {
        mvpView.showLoading()
        compositeDisposable.add(
                dataManager
                        .resolveBankAccount(accountNumber, bankCode)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            mvpView.hideLoading()
                            mvpView.onResolveAccountNumberSuccessful(it)
                        }) {
                            Log.e(it, "FundPresenter->resolveAccountNumber")
                            mvpView.hideLoading()
                            mvpView.onResolveAccountNumberFailed(it)
                        }
        )
    }

    override fun loadBanks() {
        mvpView.showLoading()
        compositeDisposable.add(
                dataManager
                        .getBanks()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            mvpView.hideLoading()
                            mvpView.onLoadBanksSuccessful(it)
                        }) {
                            mvpView.hideLoading()
                            mvpView.onLoadBanksFailed(it)
                        }
        )
    }
}