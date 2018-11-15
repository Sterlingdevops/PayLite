package com.sterlingng.paylite.data.model.realms

import com.sterlingng.paylite.data.model.User
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserRealm : RealmObject() {

    @PrimaryKey
    lateinit var phone: String
    lateinit var email: String
    lateinit var accesstoken: String
    lateinit var firstname: String
    lateinit var lastname: String
    lateinit var gender: String
    lateinit var dob: String

    fun asUser(): User {
        val user = User()
        user.dob = dob
        user.email = email
        user.gender = gender
        user.lastName = lastname
        user.phoneNumber = phone
        user.firstName = firstname
        user.accessToken = accesstoken
        return user
    }
}