package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.utils.AppUtils.gson

class PaymentMethod {
    var number: String = ""
    var name: String = ""
    var expiry: String? = ""
    var image: Int = 0

    override fun toString(): String {
        return gson.toJson(this)
    }
}