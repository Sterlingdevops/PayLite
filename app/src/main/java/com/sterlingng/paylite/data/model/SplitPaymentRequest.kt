package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.utils.AppUtils.gson

class SplitPaymentRequest {
    var amount: String = ""
    var split = ArrayList<SplitPerson>()

    override fun toString(): String {
        return gson.toJson(this)
    }
}

class SplitPerson {
    var note: String = ""
    var email: String = ""
    var phone: String = ""
    var username: String = ""
    var amount: Int = 0

    override fun toString(): String {
        return gson.toJson(this)
    }
}
