package com.sterlingng.paylite.data.model.realms

import com.sterlingng.paylite.data.model.Card
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CardRealm : RealmObject() {
    @PrimaryKey
    lateinit var number: String
    var default: Boolean = false
    lateinit var expiry: String
    lateinit var name: String

    fun asCard(): Card {
        val card = Card()
        card.name = name
        card.number = number
        card.expiry = expiry
        card.default = default
        return card
    }
}