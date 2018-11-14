package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.MenuItem
import com.sterlingng.paylite.data.model.Notification
import com.sterlingng.paylite.data.model.PaymentCategory
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MockHelper @Inject
internal constructor() : MockerInterface {

    override fun mockMenuItems(): ArrayList<MenuItem> {
        val items = ArrayList<MenuItem>()
        items += MenuItem(R.drawable.icon_send_money_color, "Send Money", "Send Money")
        items += MenuItem(R.drawable.icon_request_money, "Request Money", "Send Money")
        items += MenuItem(R.drawable.icon_get_cash, "Get Cash", "Send Money")
        items += MenuItem(R.drawable.icon_split_cost_color, "Split Cost", "Send Money")
        items += MenuItem(R.drawable.icon_scheduled_payments, "Scheduled Payments", "Send Money")
        items += MenuItem(R.drawable.icon_services_color, "Services", "Send Money")
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
