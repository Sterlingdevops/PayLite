package com.sterlingng.paylite.ui.login

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LogInPresenter<V : LogInMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), LogInMvpContract<V> {

    override fun doForgotPassword() {
        mvpView.show("Forgot Password Action", true)
    }

    override fun doLogIn() {
        mvpView.show("Login Action", true)
    }
}