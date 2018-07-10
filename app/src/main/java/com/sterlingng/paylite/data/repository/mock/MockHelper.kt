package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.*
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class MockHelper @Inject
internal constructor() : MockerInterface {

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

    override fun mockNotifications(): Observable<ArrayList<Notification>> {
        val notifications: ArrayList<Notification> = ArrayList()
        notifications += Notification(Date(), "", R.drawable.button_light_green)
        notifications += Notification(Date(), "", R.drawable.button_light_green)
        notifications += Notification(Date(), "", R.drawable.button_light_green)
        notifications += Notification(Date(), "", R.drawable.button_light_green)
        notifications += Notification(Date(), "", R.drawable.button_light_green)
        notifications += Notification(Date(), "", R.drawable.button_light_green)
        notifications += Notification(Date(), "", R.drawable.button_light_green)
        notifications += Notification(Date(), "", R.drawable.button_light_green)
        notifications += Notification(Date(), "", R.drawable.button_light_green)
        notifications += Notification(Date(), "", R.drawable.button_light_green)
        return Observable.just(notifications)
    }

    override fun mockCategories(): Observable<ArrayList<Category>> {
        val categories: ArrayList<Category> = ArrayList()
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        return Observable.just(categories)
    }

    override fun mockCharities(): Observable<ArrayList<Charity>> {
        val charities: ArrayList<Charity> = ArrayList()
        charities += Charity("Red Cross Society", "Health")
        charities += Charity("S.O.S Village", "Orphanage")
        charities += Charity("Sacred Heart", "NGO")
        charities += Charity("S.T.E.R", "women and children")
        charities += Charity("Lifebank", "Donation / disaster relief")
        charities += Charity("Trauma Care", "Health")
        return Observable.just(charities)
    }

    override fun mockDeals(): Observable<ArrayList<Deal>> {
        val deals: ArrayList<Deal> = ArrayList()
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        return Observable.just(deals)
    }
}
