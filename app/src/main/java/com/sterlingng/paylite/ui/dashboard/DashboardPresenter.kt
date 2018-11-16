package com.sterlingng.paylite.ui.dashboard

import com.google.gson.reflect.TypeToken
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Bank
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.repository.remote.DisposableObserver
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.AppUtils.gson
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by rtukpe on 21/03/2018.
 */

class DashboardPresenter<V : DashboardMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) :
        BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), DashboardMvpContract<V> {

    override fun loadBanks() {
        dataManager.getBanks("Bearer ${dataManager.getCurrentUser()?.accessToken!!}")
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : DisposableObserver() {
                    override fun onAuthorizationError() {
                        mvpView.logout()
                    }

                    override fun onRequestSuccessful(response: Response, message: String) {
                        val type = object : TypeToken<ArrayList<Bank>>() {}.type
                        val banks = gson.fromJson<ArrayList<Bank>>(gson.toJson(response.data), type)
                        mvpView.onGetBanksSuccessful(banks)
                    }

                    override fun onRequestFailed(code: Int, failureReason: String) {
                        mvpView.onGetBanksFailed()
                    }
                })
    }
}
