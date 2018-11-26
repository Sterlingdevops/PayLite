package com.sterlingng.paylite.ui.notifications

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NotificationPresenter<V : NotificationMvpView> @Inject
internal constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), NotificationMvpContract<V> {

    override fun loadNotifications() {
        mvpView.showLoading()
        mvpView.updateNotifications(dataManager.mockNotifications())
        mvpView.hideLoading()
    }
}