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
