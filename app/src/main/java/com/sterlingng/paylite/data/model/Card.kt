package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.data.model.realms.CardRealm
import com.sterlingng.paylite.utils.AppUtils

class Card {
    var name: String = ""
    var number: String = ""
    var expiry: String = ""
    var default = false

    fun asPaymentMethod(): PaymentMethod {
        val paymentMethod = PaymentMethod()
        paymentMethod.expiry = expiry
        paymentMethod.number = number
        paymentMethod.name = name
        paymentMethod.image = 0
        return paymentMethod
    }

    fun asCardRealm(): CardRealm {
        val cardRealm = CardRealm()
        cardRealm.name = name
        cardRealm.number = number
        cardRealm.expiry = expiry
        cardRealm.default = default
        return cardRealm
    }

    override fun toString(): String {
        return AppUtils.gson.toJson(this)
    }
}