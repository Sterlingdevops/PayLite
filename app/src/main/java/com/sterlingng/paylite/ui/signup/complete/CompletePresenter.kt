package com.sterlingng.paylite.ui.signup.complete

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CompletePresenter<V : CompleteMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), CompleteMvpContract<V> {

    override fun initView() {
        mvpView.setUsername(dataManager.getCurrentUser()?.firstName!!)
    }
}