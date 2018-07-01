package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.*
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MockHelper @Inject
internal constructor() : MockerInterface {
    override fun mockResults(): Observable<ArrayList<Result>> {
        val bids: ArrayList<Result> = ArrayList()
        bids += Result(500000, "Selling at EUR 770")
        bids += Result(40000, "Selling at USD 356")
        bids += Result(100000, "Selling at EUR 770")
        bids += Result(200, "Selling at USD 367")
        bids += Result(5000, "Selling at CNY 479")
        bids += Result(2000, "Selling at EUR 700")
        bids += Result(5000, "Selling at EUR 690")
        bids += Result(1000000, "Selling at GBP 485")
        return Observable.just(bids)
    }

    override fun mockTransactions(): Observable<ArrayList<Transaction>> {
        val transactions: ArrayList<Transaction> = ArrayList()
        transactions += Transaction("11,000", "Raymond Tukpe", Transaction.TransactionType.Credit, Calendar.getInstance().time)
        transactions += Transaction("3,000", "Emmanuella Esezobor", Transaction.TransactionType.Debit, Calendar.getInstance().time)
        transactions += Transaction("110,000", "Isma'il Shomala", Transaction.TransactionType.Credit, Calendar.getInstance().time)
        transactions += Transaction("100,000", "Afitafo Akande", Transaction.TransactionType.Credit, Calendar.getInstance().time)
        transactions += Transaction("10,000", "Sylas Adewale", Transaction.TransactionType.Debit, Calendar.getInstance().time)
        transactions += Transaction("1,000", "Eniola Adegoke", Transaction.TransactionType.Credit, Calendar.getInstance().time)
        transactions += Transaction("122,000", "Chiebuka Obumselu", Transaction.TransactionType.Debit, Calendar.getInstance().time)
        transactions += Transaction("11,000", "Ebun Fasina", Transaction.TransactionType.Debit, Calendar.getInstance().time)
        return Observable.just(transactions)
    }

    override fun mockAccounts(): Observable<ArrayList<Account>> {
        val accounts: ArrayList<Account> = ArrayList()
        accounts += Account("1101992229", "Sterling Bank")
        accounts += Account("1231292329", "Diamond Bank")
        accounts += Account("1102322229", "GTB")
        accounts += Account("0011992229", "FCMB")
        accounts += Account("7120199229", "Access Bank")
        accounts += Account("2019922229", "First Bank")
        accounts += Account("5601099329", "Sterling Bank")
        return Observable.just(accounts)
    }

    override fun mockCreditCards(): Observable<ArrayList<Card>> {
        val cards: ArrayList<Card> = ArrayList()
        cards += Card("Visa - 1029", "12/22", "NGN", "000")
        cards += Card("Mastercard - 2322", "12/23", "NGN", "001")
        cards += Card("Verve - 0872", "12/24", "GBP", "010")
        cards += Card("Mastercard - 2792", "02/19", "CNY", "011")
        cards += Card("Visa - 9899", "11/20", "USD", "100")
        cards += Card("Amex - 9182", "05/21", "EUR", "101")
        return Observable.just(cards)
    }

    override fun mockBids(): Observable<ArrayList<Bid>> {
        val bids: ArrayList<Bid> = ArrayList()
        bids += Bid(500000, "Selling at EUR 770")
        bids += Bid(40000, "Selling at USD 356")
        bids += Bid(100000, "Selling at EUR 770")
        bids += Bid(200, "Selling at USD 367")
        bids += Bid(5000, "Selling at CNY 479")
        bids += Bid(2000, "Selling at EUR 700")
        bids += Bid(5000, "Selling at EUR 690")
        bids += Bid(1000000, "Selling at GBP 485")
        return Observable.just(bids)
    }

    override fun mockRates(): Observable<ArrayList<Rate>> {
        val rates: ArrayList<Rate> = ArrayList()
        rates += Rate(Calendar.getInstance().time, "360/362", "480/490", "418/424", "418/424")
        rates += Rate(Calendar.getInstance().time, "360/362", "480/490", "418/424", "418/424")
        rates += Rate(Calendar.getInstance().time, "360/362", "480/490", "418/424", "418/424")
        rates += Rate(Calendar.getInstance().time, "360/362", "480/490", "418/424", "418/424")
        rates += Rate(Calendar.getInstance().time, "360/362", "480/490", "418/424", "418/424")
        rates += Rate(Calendar.getInstance().time, "360/362", "480/490", "418/424", "418/424")
        rates += Rate(Calendar.getInstance().time, "360/362", "480/490", "418/424", "418/424")
        return Observable.just(rates)
    }
}
