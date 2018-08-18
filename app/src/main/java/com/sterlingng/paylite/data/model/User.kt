package com.sterlingng.paylite.data.model

import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.data.model.realms.UserRealm
import com.sterlingng.paylite.utils.AppUtils.gson

open class User {
    @SerializedName("bvn")
    var bvn: String = ""

    @SerializedName("_id")
    var userId: String = ""

    @SerializedName("email")
    var email: String = ""

    @SerializedName("username")
    var username: String = ""

    @SerializedName("latitude")
    var latitude: String = ""

    @SerializedName("longitude")
    var longitude: String = ""

    @SerializedName("last_name")
    var lastName: String = ""

    @SerializedName("first_name")
    var firstName: String = ""

    @SerializedName("phone_number")
    var phoneNumber: String = ""

    @SerializedName("security_token")
    var token: String = ""

    @SerializedName("wallet")
    var wallet: String = ""

    override fun toString(): String {
        return gson.toJson(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is User) other.userId == userId else false
    }

    fun asUserRealm(): UserRealm {
        val userRealm = UserRealm()
        userRealm.bvn = bvn
        userRealm.email = email
        userRealm.userid = userId
        userRealm.wallet = wallet
        userRealm.phone = phoneNumber
        userRealm.lastname = lastName
        userRealm.username = username
        userRealm.latitude = latitude
        userRealm.firstname = firstName
        userRealm.longitude = longitude
        userRealm.token = token
        userRealm.wallet = wallet
        return userRealm
    }

    override fun hashCode(): Int {
        var result = bvn.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + token.hashCode()
        return result
    }
}
