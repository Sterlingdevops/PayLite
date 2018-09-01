package com.sterlingng.paylite.data.repository.mock

import com.google.gson.reflect.TypeToken
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.*
import com.sterlingng.paylite.utils.AppUtils.gson
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MockHelper @Inject
internal constructor() : MockerInterface {

    private val banks = "[\n" +
            "    {\n" +
            "        \"name\": \"Access Bank\",\n" +
            "        \"code\": \"044\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"ALAT by WEMA\",\n" +
            "        \"code\": \"035A\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Citibank Nigeria\",\n" +
            "        \"code\": \"023\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Diamond Bank\",\n" +
            "        \"code\": \"063\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Ecobank Nigeria\",\n" +
            "        \"code\": \"050\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Enterprise Bank\",\n" +
            "        \"code\": \"084\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Fidelity Bank\",\n" +
            "        \"code\": \"070\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"First Bank of Nigeria\",\n" +
            "        \"code\": \"011\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"First City Monument Bank\",\n" +
            "        \"code\": \"214\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Guaranty Trust Bank\",\n" +
            "        \"code\": \"058\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Heritage Bank\",\n" +
            "        \"code\": \"030\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Jaiz Bank\",\n" +
            "        \"code\": \"301\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Keystone Bank\",\n" +
            "        \"code\": \"082\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"MainStreet Bank\",\n" +
            "        \"code\": \"014\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Parallex Bank\",\n" +
            "        \"code\": \"526\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Providus Bank\",\n" +
            "        \"code\": \"101\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Skye Bank\",\n" +
            "        \"code\": \"076\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Stanbic IBTC Bank\",\n" +
            "        \"code\": \"221\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Standard Chartered Bank\",\n" +
            "        \"code\": \"068\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Sterling Bank\",\n" +
            "        \"code\": \"232\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Suntrust Bank\",\n" +
            "        \"code\": \"100\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Union Bank of Nigeria\",\n" +
            "        \"code\": \"032\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"United Bank For Africa\",\n" +
            "        \"code\": \"033\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Unity Bank\",\n" +
            "        \"code\": \"215\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Wema Bank\",\n" +
            "        \"code\": \"035\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Zenith Bank\",\n" +
            "        \"code\": \"057\"\n" +
            "    }\n" +
            "]"

    override fun mockBanks(): ArrayList<Bank> {
        val type = object : TypeToken<ArrayList<Bank>>() {}.type
        return gson.fromJson(banks, type)
    }

    override fun mockContacts(): ArrayList<PayliteContact> {
        val contacts = ArrayList<PayliteContact>()
        contacts += PayliteContact("Tukpe", "Raymond", R.color.light_green, R.color.blue_black_dark)
        contacts += PayliteContact("Oluyebi", "Dara", R.color.light_blue, R.color.dark_sage)
        contacts += PayliteContact("See", "all", R.drawable.phone_book, R.color.dark_sage)
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
