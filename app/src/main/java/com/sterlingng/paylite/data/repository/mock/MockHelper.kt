package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.*
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class MockHelper @Inject
internal constructor() : MockerInterface {

    override fun mockLogin(email: String, password: String): Observable<User> {
        return Observable.just(User("email", password, null))
    }

    override fun mockTransactions(): ArrayList<Transaction> {
        val transactions: ArrayList<Transaction> = ArrayList()
        transactions += Transaction("11,000", "Income", Transaction.TransactionType.Credit, "2 transactions", Date(1524149746L))
        transactions += Transaction("3,000", "General", Transaction.TransactionType.Debit, "5 transactions", Date(1524128746L))
        transactions += Transaction("110,000", "Vacation", Transaction.TransactionType.Debit, "1 transaction", Date(1024328746L))
        transactions += Transaction("100,000", "Wedding Gifts", Transaction.TransactionType.Credit, "2 transactions", Date(1522128746L))
        transactions += Transaction("10,000", "Income", Transaction.TransactionType.Credit, "2 transactions", Date(1524128726L))
        transactions += Transaction("1,000", "Side Hustle", Transaction.TransactionType.Credit, "3 transactions", Date(1524128746L))
        transactions += Transaction("122,000", "Allowance", Transaction.TransactionType.Debit, "4 transactions", Date(1524178746L))
        transactions += Transaction("12,000", "Allowance", Transaction.TransactionType.Debit, "1 transaction", Date(1224128766L))
        transactions += Transaction("14,000", "Side Hustle", Transaction.TransactionType.Credit, "1 transaction", Date(1526128766L))
        transactions += Transaction("17,000", "Allowance", Transaction.TransactionType.Debit, "1 transaction", Date(1529128766L))
        transactions += Transaction("11,000", "Allowance", Transaction.TransactionType.Credit, "1 transaction", Date(1524128866L))
        return transactions
    }

    override fun mockNotifications(): ArrayList<Notification> {
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
        return notifications
    }

    override fun mockProjects(): ArrayList<Project> {
        val projects: ArrayList<Project> = ArrayList()
        projects += Project("Irrigation 2018", "Agriculture")
        projects += Project("Children’s day", "Health")
        projects += Project("Irrigation 2018", "Agriculture")
        projects += Project("Children’s day", "Health")
        projects += Project("Irrigation 2018", "Agriculture")
        projects += Project("Children’s day", "Health")
        projects += Project("Irrigation 2018", "Agriculture")
        projects += Project("Children’s day", "Health")
        projects += Project("Irrigation 2018", "Agriculture")
        projects += Project("Children’s day", "Health")
        projects += Project("Irrigation 2018", "Agriculture")
        projects += Project("Children’s day", "Health")
        projects += Project("Irrigation 2018", "Agriculture")
        projects += Project("Children’s day", "Health")
        return projects
    }

    override fun mockCategories(): ArrayList<PaymentCategory> {
        val categories: ArrayList<PaymentCategory> = ArrayList()
        categories += PaymentCategory("Bills", R.color.black_effective)
        categories += PaymentCategory("Shopping", R.color.yellow)
        categories += PaymentCategory("Transportation", R.color.light_red)
        categories += PaymentCategory("Education", R.color.scarlet_dark)
        categories += PaymentCategory("Investments", R.color.scarlet)
        categories += PaymentCategory("Groceries", R.color.light_green)
        categories += PaymentCategory("Eating Out", R.color.blue_grey)
        categories += PaymentCategory("Travel", R.color.gray)
        categories += PaymentCategory("Income", R.color.greenish_grey)
        categories += PaymentCategory("Charity", R.color.blueberry)
        return categories
    }

    override fun mockCharities(): ArrayList<Charity> {
        val charities: ArrayList<Charity> = ArrayList()
        charities += Charity("Red Cross Society", "Health")
        charities += Charity("S.O.S Village", "Orphanage")
        charities += Charity("Sacred Heart", "NGO")
        charities += Charity("S.T.E.R", "women and children")
        charities += Charity("LifeBank", "Donation / disaster relief")
        charities += Charity("Trauma Care", "Health")
        return charities
    }

    override fun mockDeals(): ArrayList<Deal> {
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
        return deals
    }
}
