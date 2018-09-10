package com.sterlingng.paylite.data.model.realms

import com.sterlingng.paylite.data.model.User
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserRealm : RealmObject() {

    @PrimaryKey
    lateinit var phone: String
    lateinit var email: String
    lateinit var lastname: String
    lateinit var firstname: String
    lateinit var accesstoken: String

    fun asUser(): User {
        val user = User()
        user.email = email
        user.lastName = lastname
        user.phoneNumber = phone
        user.firstName = firstname
        user.accessToken = accesstoken
        return user
    }
}