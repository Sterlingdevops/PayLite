package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.*

interface MockerInterface {
    fun mockBanks(): ArrayList<Bank>
    fun mockDeals(): ArrayList<Deal>
    fun mockProjects(): ArrayList<Project>
    fun mockContacts(): ArrayList<Contact>
    fun mockCharities(): ArrayList<Charity>
    fun mockTransactions(): ArrayList<Transaction>
    fun mockNotifications(): ArrayList<Notification>
    fun mockCategories(): ArrayList<PaymentCategory>
    fun mockPaymentMethods(): ArrayList<PaymentMethod>
}
