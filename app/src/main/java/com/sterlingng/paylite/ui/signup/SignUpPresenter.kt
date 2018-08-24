package com.sterlingng.paylite.ui.signup

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.utils.Log
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by rtukpe on 21/03/2018.
 */

class SignUpPresenter<V : SignUpMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SignUpMvpContract<V> {

    override fun prepServer() {
        compositeDisposable.add(
                dataManager.getWallet("", "")
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({

                        }) {
                            Log.e(it, "SignUpPresenter->prepServer")
                        }
        )
    }
}