package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.AppUtils.isJSONValid
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
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
        dataManager.getWallet()?.let { mvpView.onGetWalletSuccessful(it) }
        mvpView.initView(dataManager.getCurrentUser())
    }

    override fun loadWallet() {
        val user = dataManager.getCurrentUser()
        compositeDisposable.add(
                dataManager.getWallet(user?.username!!)
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
                                if (isJSONValid(raw!!)) {
                                    return@onErrorReturn gson.fromJson(raw, Response::class.java)
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
                            if (it.message == "successful") {
                                val wallet = gson.fromJson(gson.toJson(it.data), Wallet::class.java)
                                dataManager.saveWallet(wallet)
                                mvpView.onGetWalletSuccessful(wallet)
                            } else {
                                mvpView.onGetWalletFailed(it)
                            }
                        }
        )
    }
}