package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MockHelper @Inject
internal constructor() : MockerInterface {

    override fun mockContacts(): ArrayList<Contact> {
        val contacts = ArrayList<Contact>()
        contacts += Contact("Tukpe", "Raymond", R.color.light_green, R.color.blue_black_dark)
        contacts += Contact("Oluyebi", "Dara", R.color.light_blue, R.color.dark_sage)
        contacts += Contact("See", "all", R.drawable.phone_book, R.color.dark_sage)
        return contacts
    }

    override fun mockPaymentMethods(): ArrayList<PaymentMethod> {
        val cards: ArrayList<PaymentMethod> = ArrayList()
        cards += PaymentMethod("Visa (1029)", "Eleanor Ezimah", "12/22", R.drawable.visa)
        cards += PaymentMethod("Mastercard (2322)", "Raymond Tukpe", "12/23", R.drawable.mastercard)
        cards += PaymentMethod("Savings Account (0247966933)", "Shomala Ismail", null, R.drawable.cashout_bank)
        cards += PaymentMethod("Mastercard (2792)", "Pierre-Emerick Aubameyang", "02/19", R.drawable.mastercard)
        cards += PaymentMethod("Visa (9899)", "Daivd Luis", "11/20", R.drawable.visa)
        cards += PaymentMethod("Savings Account (123384844)", "Deigo Costa", null, R.drawable.cashout_bank)
        return cards
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
