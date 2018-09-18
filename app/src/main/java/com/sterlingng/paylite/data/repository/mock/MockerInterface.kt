package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.*

interface MockerInterface {
    fun mockBanks(): ArrayList<Bank>
    fun mockContacts(): ArrayList<PayliteContact>
    fun mockNotifications(): ArrayList<Notification>
    fun mockCategories(): ArrayList<PaymentCategory>
    fun mockPaymentMethods(): ArrayList<PaymentMethod>
}
