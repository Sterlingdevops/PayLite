package com.sterlingng.paylite.ui.transactions.categories

import com.google.gson.reflect.TypeToken
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TransactionCategoriesPresenter<V : TransactionCategoriesMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), TransactionCategoriesMvpContract<V> {

    override fun onViewInitialized() {
        super.onViewInitialized()
        mvpView.initView(dataManager.getTransactions())
    }

    override fun loadTransactions() {
        val startDate = "2018-01-01"
        val endDate = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE).format(Date(System.currentTimeMillis()))
        dataManager.getUserTransactions(dataManager.getCurrentUser()?.phoneNumber!!,
                endDate, startDate, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}")
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        val type = object : TypeToken<ArrayList<Transaction>>() {}.type
                        val transactions =
                                gson.fromJson<ArrayList<Transaction>>(gson.toJson(response.data), type)
                        dataManager.saveTransactions(transactions)
                        mvpView.onGetUserTransactionsSuccessful(transactions)
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onGetUserTransactionsFailed()
                    }
                })
    }
}
