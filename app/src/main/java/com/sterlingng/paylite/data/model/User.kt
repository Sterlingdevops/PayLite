package com.sterlingng.paylite.data.model

import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.data.model.realms.UserRealm
import com.sterlingng.paylite.utils.AppUtils.gson

open class User {
    @SerializedName("Bvn")
    var bvn: String = ""

    @SerializedName("Email")
    var email: String = ""

    @SerializedName("HandleUsername")
    var username: String = ""

    @SerializedName("latitude")
    var latitude: String = ""

    @SerializedName("longitude")
    var longitude: String = ""

    @SerializedName("LastName")
    var lastName: String = ""

    @SerializedName("FirstName")
    var firstName: String = ""

    @SerializedName("Mobile")
    var phoneNumber: String = ""

    override fun toString(): String {
        return gson.toJson(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is User) other.bvn == bvn else false
    }

    fun asUserRealm(): UserRealm {
        val userRealm = UserRealm()
        userRealm.bvn = bvn
        userRealm.email = email
        userRealm.phone = phoneNumber
        userRealm.lastname = lastName
        userRealm.username = username
        userRealm.latitude = latitude
        userRealm.firstname = firstName
        userRealm.longitude = longitude
        return userRealm
    }

    override fun hashCode(): Int {
        var result = bvn.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        return result
    }
}
