package com.sterlingng.paylite.ui.fund

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Bank
import com.sterlingng.paylite.data.model.Card
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.sha256
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import kotlin.collections.set


class FundPresenter<V : FundMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), FundMvpContract<V> {

    override fun saveBank(bank: Bank) {
        dataManager.saveBank(bank)
    }

    override fun saveCard(card: Card) {
        dataManager.saveCard(card)
    }

    override fun loadCachedWallet() {
        dataManager.getWallet()?.let { mvpView.initView(it) }
    }

    override fun loadWallet() {
        mvpView.showLoading()
        val user = dataManager.getCurrentUser()

        dataManager.getWallet(user?.phoneNumber!!, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}")
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        val wallet = gson.fromJson(gson.toJson(response.data), Wallet::class.java)
                        dataManager.saveWallet(wallet)
                        mvpView.onGetWalletSuccessful(wallet)
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onGetWalletFailed()
                    }
                })
    }

    override fun fundWalletWithCard(data: HashMap<String, Any>) {
        dataManager.getCurrentUser()?.phoneNumber?.let { data["mobile"] = it }
        dataManager.getWallet()?.walletId?.let { data["customerId"] = it }
        mvpView.showLoading()
        dataManager
                .fundWalletWithCard(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onFundWalletSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onFundWalletFailed()
                        mvpView.hideLoading()
                    }
                })

    }

    override fun fundWalletWithBankAccount(data: HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        data["toacct"] = user?.phoneNumber!!

        mvpView.showLoading()
        dataManager
                .fundWalletWithBankAccount(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}", gson.toJson(data).sha256())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        val wallet = gson.fromJson(gson.toJson(response.data), Wallet::class.java)
                        dataManager.saveWallet(wallet)
                        mvpView.onFundWalletSuccessful()
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onFundWalletFailed()
                        mvpView.hideLoading()
                    }
                })
    }

    override fun resolveCardNumber(bin: String) {
        dataManager
                .resolveCardNumber(bin)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onResolveCardNumberSuccessful(response)
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onResolveCardNumberFailed()
                    }
                })
    }

    override fun resolveAccountNumber(accountNumber: String, bankCode: String) {
        mvpView.showLoading()
        dataManager
                .resolveBankAccount(accountNumber, bankCode)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        mvpView.onResolveAccountNumberSuccessful(response)
                        mvpView.hideLoading()
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onResolveAccountNumberFailed()
                        mvpView.hideLoading()
                    }
                })
    }
}