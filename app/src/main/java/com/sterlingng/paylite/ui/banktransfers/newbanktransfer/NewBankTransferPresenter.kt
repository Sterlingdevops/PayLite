package com.sterlingng.paylite.ui.banktransfers.newbanktransfer

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.BankNameEnquiry
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.AppUtils.gson
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class NewBankTransferPresenter<V : NewBankTransferMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), NewBankTransferMvpContract<V> {

    override fun resolveBankAccount(accountNumber: String, bankCode: String) {
        mvpView.showLoading()
        compositeDisposable.add(
                dataManager
                        .bankNameEnquiry(accountNumber, bankCode, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}")
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
                                    val response = AppUtils.gson.fromJson(raw, Response::class.java)
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
                        .subscribe {
                            if (it.response != null && it.response == "00") {
                                val bankNameEnquiry: BankNameEnquiry = gson.fromJson(gson.toJson(it.data), BankNameEnquiry::class.java)
                                mvpView.onResolveBankAccountSuccessful(bankNameEnquiry)
                            } else {
                                if (it.code == 401) {
                                    mvpView.logout()
                                } else {
                                    mvpView.onResolveBankAccountFailed()
                                }
                            }
                            mvpView.hideLoading()
                        }
        )
    }
}