package com.sterlingng.paylite.ui.banktransfers.newbanktransfer

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.CashOutToBankAccountRequest
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewBankTransferPresenter<V : NewBankTransferMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), NewBankTransferMvpContract<V> {

    override fun resolveBankAccount(accountNumber: String, bankCode: String) {
        mvpView.showLoading()
        dataManager
                .bankNameEnquiry(accountNumber, bankCode, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}")
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        val cashOutToBankAccountRequest: CashOutToBankAccountRequest =
                                gson.fromJson(gson.toJson(response.data), CashOutToBankAccountRequest::class.java)
                        mvpView.onResolveBankAccountSuccessful(cashOutToBankAccountRequest)
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onResolveBankAccountFailed()
                        mvpView.hideLoading()
                    }
                })
    }
}