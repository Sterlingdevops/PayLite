package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.MenuItem
import com.sterlingng.paylite.data.model.Notification
import com.sterlingng.paylite.data.model.PaymentCategory

interface MockerInterface {
    fun mockMenuItems(): ArrayList<MenuItem>
    fun mockNotifications(): ArrayList<Notification>
    fun mockCategories(): ArrayList<PaymentCategory>
}
