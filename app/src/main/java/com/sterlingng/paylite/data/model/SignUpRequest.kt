package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.utils.AppUtils.gson

class SignUpRequest {
    var dob: String = ""
    var email: String = ""
    var gender: String = ""
    var password: String = ""
    var lastName: String = ""
    var firstName: String = ""
    var phoneNumber: String = ""

    override fun toString(): String {
        return gson.toJson(this)
    }

    fun toHashMap(): HashMap<String, Any> {
        val data = HashMap<String, Any>()
        data["DOB"] = dob
        data["email"] = email
        data["Gender"] = gender
        data["Password"] = password
        data["lastname"] = lastName
        data["mobile"] = phoneNumber
        data["firstname"] = firstName

        data["latitude"] = "0"
        data["longitude"] = "0"

        return data
    }
}