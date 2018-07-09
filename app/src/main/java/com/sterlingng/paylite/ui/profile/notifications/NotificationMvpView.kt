package com.sterlingng.paylite.ui.profile.notifications

import com.sterlingng.paylite.data.model.Notification
import com.sterlingng.paylite.ui.base.MvpView

interface NotificationMvpView : MvpView {
    fun updateNotifications(it: ArrayList<Notification>)
}
