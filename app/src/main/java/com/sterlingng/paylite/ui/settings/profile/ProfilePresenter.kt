package com.sterlingng.paylite.ui.settings.profile

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProfilePresenter<V : ProfileMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), ProfileMvpContract<V> {

    override fun logOut() {
        dataManager.deleteAllScheduledPayments()
        dataManager.deleteAllTransactions()
        mvpView.onLogOutComplete()
    }

    override fun onViewInitialized() {
        super.onViewInitialized()
        dataManager.getCurrentUser()?.let { mvpView.initView(it) }
    }
}