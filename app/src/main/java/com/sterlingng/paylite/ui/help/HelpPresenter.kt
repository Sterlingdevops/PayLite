package com.sterlingng.paylite.ui.help

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HelpPresenter<V : HelpMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), HelpMvpContract<V> {

    override fun getUserName(): String {
        dataManager.getCurrentUser()?.let { return it.firstName + " " + it.lastName }
        return ""
    }

    override fun getUserPhone(): String {
        dataManager.getCurrentUser()?.let { return it.phoneNumber }
        return ""
    }
}