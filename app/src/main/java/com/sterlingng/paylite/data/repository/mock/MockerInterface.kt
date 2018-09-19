package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.Bank
import com.sterlingng.paylite.data.model.Notification
import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.data.model.PaymentCategory

interface MockerInterface {
    fun mockBanks(): ArrayList<Bank>
    fun mockContacts(): ArrayList<PayliteContact>
    fun mockNotifications(): ArrayList<Notification>
    fun mockCategories(): ArrayList<PaymentCategory>
}
