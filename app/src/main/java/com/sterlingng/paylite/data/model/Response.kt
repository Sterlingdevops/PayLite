package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.utils.AppUtils.gson

class Response {
    var code: Int = 0
    var status: Boolean = false
    var response: String? = ""
    var message: String? = ""
    var data: Any? = Any()

    override fun toString(): String {
        return gson.toJson(this)
    }
}