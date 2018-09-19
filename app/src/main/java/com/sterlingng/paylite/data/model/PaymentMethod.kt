package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.utils.AppUtils.gson

class PaymentMethod {
    val number: String = ""
    val name: String = ""
    val expiry: String? = ""
    val image: Int = 0

    override fun toString(): String {
        return gson.toJson(this)
    }
}