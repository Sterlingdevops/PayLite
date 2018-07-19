package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.*
import io.reactivex.Observable

interface MockerInterface {
    fun mockNotifications(): Observable<ArrayList<Notification>>
    fun mockCharities(): Observable<ArrayList<Charity>>
    fun mockCategories(): Observable<ArrayList<Category>>
    fun mockDeals(): Observable<ArrayList<Deal>>
    fun mockTransactions(): Observable<ArrayList<Transaction>>
    fun mockLogin(email: String, password: String) : Observable<User>
}
