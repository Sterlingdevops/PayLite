package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.utils.AppUtils.gson

class ContactItem {
    var contact: String = ""
    var amount: String = ""

    override fun toString(): String {
        return gson.toJson(this)
    }
}