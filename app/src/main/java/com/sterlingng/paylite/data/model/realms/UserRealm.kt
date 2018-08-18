package com.sterlingng.paylite.data.model.realms

import com.sterlingng.paylite.data.model.User
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserRealm : RealmObject() {

    @PrimaryKey
    lateinit var userid: String
    lateinit var bvn: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var token: String
    lateinit var wallet: String
    lateinit var latitude: String
    lateinit var username: String
    lateinit var lastname: String
    lateinit var longitude: String
    lateinit var firstname: String

    fun asUser(): User {
        val user = User()
        user.bvn = bvn
        user.email = email
        user.token = token
        user.userId = userid
        user.wallet = wallet
        user.phoneNumber = phone
        user.lastName = lastname
        user.username = username
        user.latitude = latitude
        user.firstName = firstname
        user.longitude = longitude
        return user
    }
}