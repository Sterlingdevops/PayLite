package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MockHelper @Inject
internal constructor() : MockerInterface {
    override fun mockVAS(): ArrayList<VAService> {
        val vas = ArrayList<VAService>()
//        val utilityProviders = ArrayList<VasProvider>()
//        utilityProviders += VasProvider("Airtel", R.drawable.icon_airtel)
//        utilityProviders += VasProvider("MTN", R.drawable.icon_mtn)
//        utilityProviders += VasProvider("Glo", R.drawable.icon_glo)
//        utilityProviders += VasProvider("9mobile", R.drawable.icon_9_mobile)
//        vas += VAService("Utility Bill", R.drawable.icon_utilities, utilityProviders)

        val tvProviders = ArrayList<VasProvider>()
        tvProviders += VasProvider(0, "Kwese", R.drawable.icon_kwese)
        tvProviders += VasProvider(1, "Iroko Tv", R.drawable.icon_iroko)
        tvProviders += VasProvider(2, "Dstv", R.drawable.icon_dstv)
        tvProviders += VasProvider(3, "Gotv", R.drawable.icon_gotv)
        vas += VAService(3, "Tv Bills", R.drawable.icon_tv, tvProviders)

        val internetProviders = ArrayList<VasProvider>()
        internetProviders += VasProvider(1, "Spectranet", R.drawable.icon_spectranet)
        internetProviders += VasProvider(2, "Swift", R.drawable.icon_swift)
        internetProviders += VasProvider(3, "nTel", R.drawable.icon_ntel)
        internetProviders += VasProvider(4, "ipNX", R.drawable.icon_ipnx)
        vas += VAService(2, "Internet Bill", R.drawable.icon_internet, internetProviders)

        val airtimeProviders = ArrayList<VasProvider>()
        airtimeProviders += VasProvider(2, "Airtel", R.drawable.icon_airtel)
        airtimeProviders += VasProvider(1, "MTN", R.drawable.icon_mtn)
        airtimeProviders += VasProvider(3, "Glo", R.drawable.icon_glo)
        airtimeProviders += VasProvider(4, "9mobile", R.drawable.icon_9_mobile)
        vas += VAService(1, "Airtime & mobile data ", R.drawable.icon_airtime_data, airtimeProviders)

        return vas
    }

    override fun mockMenuItems(): ArrayList<MenuItem> {
        val items = ArrayList<MenuItem>()
        items += MenuItem(R.drawable.icon_send_money, "Send Money")
        items += MenuItem(R.drawable.icon_request_money, "Request Money")
        items += MenuItem(R.drawable.icon_get_cash, "Get Cash")
        items += MenuItem(R.drawable.icon_split_cost, "Split Cost")
        items += MenuItem(R.drawable.icon_scheduled, "Scheduled Payments")
        return items
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

    override fun mockPaymentCategories(): ArrayList<PaymentCategory> {
        val categories: ArrayList<PaymentCategory> = ArrayList()
        categories += PaymentCategory("Bills", R.drawable.icon_bills2)
        categories += PaymentCategory("Income", R.drawable.icon_income)
        categories += PaymentCategory("Travel", R.drawable.icon_travel)
        categories += PaymentCategory("Health Care", R.drawable.icon_give)
        categories += PaymentCategory("Eating Out", R.drawable.icon_eating)
        categories += PaymentCategory("Shopping", R.drawable.icon_shopping)
        categories += PaymentCategory("Charity", R.drawable.icon_education)
        categories += PaymentCategory("Payroll", R.drawable.icon_investment)
        categories += PaymentCategory("Transfers", R.drawable.icon_transfers)
        categories += PaymentCategory("Groceries", R.drawable.icon_groceries)
        categories += PaymentCategory("Education", R.drawable.icon_education)
        categories += PaymentCategory("Investments", R.drawable.icon_investment)
        categories += PaymentCategory("Entertainment", R.drawable.icon_entertainment)
        categories += PaymentCategory("Transportation", R.drawable.icon_transportation)
        return categories
    }
}
