package com.sterlingng.paylite.ui.notifications

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface NotificationMvpContract<V : NotificationMvpView> : MvpPresenter<V> {
    fun loadNotifications()
}
