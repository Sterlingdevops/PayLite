package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.*
import io.reactivex.Observable

interface MockerInterface {
    fun mockBids(): Observable<ArrayList<Bid>>
    fun mockRates(): Observable<ArrayList<Rate>>
    fun mockResults(): Observable<ArrayList<Result>>
    fun mockCreditCards(): Observable<ArrayList<Card>>
    fun mockAccounts(): Observable<ArrayList<Account>>
    fun mockTransactions(): Observable<ArrayList<Transaction>>
}
