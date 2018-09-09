package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.utils.AppUtils.gson

class ForgotPasswordRequest {
    var newPassword: String = ""
    var mobile: String = ""
    var token: String = ""

    fun toHashMap(): HashMap<String, Any> {
        val data = HashMap<String, Any>()
        data["NewPassword"] = newPassword
        data["Otp"] = token
        data["mobile"] = "0$mobile"
        return data
    }

    override fun toString(): String {
        return gson.toJson(this)
    }
}