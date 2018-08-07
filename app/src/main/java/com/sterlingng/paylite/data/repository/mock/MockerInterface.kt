package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.*
import io.reactivex.Observable

interface MockerInterface {
    fun mockNotifications(): ArrayList<Notification>
    fun mockCharities(): ArrayList<Charity>
    fun mockProjects(): ArrayList<Project>
    fun mockDeals(): ArrayList<Deal>
    fun mockTransactions(): ArrayList<Transaction>
    fun mockLogin(email: String, password: String) : Observable<User>
    fun mockCategories(): ArrayList<PaymentCategory>
}
