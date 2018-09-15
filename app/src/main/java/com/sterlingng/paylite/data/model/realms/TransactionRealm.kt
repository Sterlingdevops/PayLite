package com.sterlingng.paylite.data.model.realms

import com.sterlingng.paylite.data.model.Transaction
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TransactionRealm : RealmObject() {
    @PrimaryKey
    lateinit var id: String
    lateinit var name: String
    lateinit var type: String
    lateinit var date: String
    lateinit var amount: String
    lateinit var reference: String

    fun asTransaction(): Transaction {
        val transaction = Transaction()
        transaction.id = id.toDouble().toInt()
        transaction.type = type
        transaction.name = name
        transaction.date = date
        transaction.amount = amount
        transaction.reference = reference
        return transaction
    }
}