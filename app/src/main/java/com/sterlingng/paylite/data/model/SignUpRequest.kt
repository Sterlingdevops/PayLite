package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.utils.AppUtils.gson

class SignUpRequest {
    var bvn: String = ""
    var email: String = ""
    var username: String = ""
    var password: String = ""
    var lastName: String = ""
    var firstName: String = ""
    var phoneNumber: String = ""

    override fun toString(): String {
        return gson.toJson(this)
    }

    fun toHashMap(): HashMap<String, Any> {
        val data = HashMap<String, Any>()
        data["bvn"] = bvn
        data["email"] = email
        data["username"] = username
        data["password"] = password
        data["last_name"] = lastName
        data["first_name"] = firstName
        data["phone_number"] = phoneNumber
        return data
    }
}