package com.sterlingng.paylite.ui.transactions.categories

import com.google.gson.reflect.TypeToken
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.AppUtils.gson
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
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
        compositeDisposable.add(
                dataManager.getUserTransactions(dataManager.getCurrentUser()?.phoneNumber!!,
                        endDate, startDate, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}")
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .onErrorReturn {
                            if (it is java.net.SocketTimeoutException) {
                                val response = Response()
                                response.data = SocketTimeoutException()
                                response.message = "Error!!! The server didn't respond fast enough and the request timed out"
                                response.response = "failed"
                                return@onErrorReturn response
                            } else {
                                val raw = (it as HttpException).response().errorBody()?.string()
                                if (AppUtils.isJSONValid(raw!!)) {
                                    val response = gson.fromJson(raw, Response::class.java)
                                    response.code = it.code()
                                    return@onErrorReturn response
                                }
                                val response = Response()
                                response.data = HttpException(retrofit2.Response.error<String>(500,
                                        ResponseBody.create(MediaType.parse("text/html; charset=utf-8"), raw)))
                                response.message = "Error!!! The server didn't respond fast enough and the request timed out"
                                response.response = "failed"
                                return@onErrorReturn response
                            }
                        }
                        .subscribe { response ->
                            if (response.response == "00") {
                                val type = object : TypeToken<ArrayList<Transaction>>() {}.type
                                val transactions =
                                        gson.fromJson<ArrayList<Transaction>>(gson.toJson(response.data), type)
                                dataManager.saveTransactions(transactions)
                                mvpView.onGetUserTransactionsSuccessful(transactions)
                            } else {
                                if (response.code == 401) {
                                    mvpView.logout()
                                } else {
                                    mvpView.onGetUserTransactionsFailed(response)
                                }
                            }
                        }
        )
    }
}
