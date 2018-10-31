package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.utils.AppUtils

class SplitPerson {
    var note: String = ""
    var email: String = ""
    var phone: String = ""
    var username: String = ""
    var amount: Int = 0

    override fun toString(): String {
        return AppUtils.gson.toJson(this)
    }
}