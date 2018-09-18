package com.sterlingng.paylite.data.model.realms

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CardRealm : RealmObject() {
    lateinit var name: String
    @PrimaryKey
    lateinit var number: String
    lateinit var expiry: String
}