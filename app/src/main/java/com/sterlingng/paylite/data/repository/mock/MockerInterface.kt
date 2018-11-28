package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.*

interface MockerInterface {
    fun mockVAS(): ArrayList<VAService>
    fun mockQuestions(): ArrayList<Question>
    fun mockMenuItems(): ArrayList<MenuItem>
    fun mockNotifications(): ArrayList<Notification>
    fun mockPaymentCategories(): ArrayList<PaymentCategory>
}
