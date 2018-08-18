package com.sterlingng.paylite.data.model

import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.utils.AppUtils.gson

class AccountDetails {
    @SerializedName("account_number")
    val number: String = ""

    @SerializedName("account_name")
    val name: String = ""

    override fun toString(): String {
        return gson.toJson(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is AccountDetails) other.number == number else false
    }

    override fun hashCode(): Int {
        var result = number.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}