package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.MenuItem
import com.sterlingng.paylite.data.model.Notification
import com.sterlingng.paylite.data.model.PaymentCategory
import com.sterlingng.paylite.data.model.VAService

interface MockerInterface {
    fun mockVAS(): ArrayList<VAService>
    fun mockMenuItems(): ArrayList<MenuItem>
    fun mockNotifications(): ArrayList<Notification>
    fun mockPaymentCategories(): ArrayList<PaymentCategory>
}
