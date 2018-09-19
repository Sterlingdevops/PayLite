package com.sterlingng.paylite.data.repository.mock

import com.google.gson.reflect.TypeToken
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Bank
import com.sterlingng.paylite.data.model.Notification
import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.data.model.PaymentCategory
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
}
