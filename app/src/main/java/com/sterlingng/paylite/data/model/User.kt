package com.sterlingng.paylite.data.model

import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.data.model.realms.UserRealm
import com.sterlingng.paylite.utils.AppUtils.gson

open class User {
    @SerializedName("Email")
    var email: String = ""

    @SerializedName("LastName")
    var lastName: String = ""

    @SerializedName("FirstName")
    var firstName: String = ""

    @SerializedName("Mobile")
    var phoneNumber: String = ""

    @SerializedName("access_token")
    var accessToken: String = ""

    override fun toString(): String {
        return gson.toJson(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is User) other.phoneNumber == phoneNumber else false
    }

    fun asUserRealm(): UserRealm {
        val userRealm = UserRealm()
        userRealm.email = email
        userRealm.phone = phoneNumber
        userRealm.lastname = lastName
        userRealm.firstname = firstName
        userRealm.accesstoken = accessToken
        return userRealm
    }

    override fun hashCode(): Int {
        var result = phoneNumber.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + accessToken.hashCode()
        return result
    }
}
