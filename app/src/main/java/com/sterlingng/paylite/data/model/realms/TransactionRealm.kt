package com.sterlingng.paylite.data.model.realms

import com.sterlingng.paylite.data.model.Transaction
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TransactionRealm : RealmObject() {
    @PrimaryKey
    lateinit var id: String
    lateinit var type: String
    lateinit var date: String
    lateinit var amount: String
    lateinit var mobile: String
    lateinit var credit: String
    lateinit var reference: String
    lateinit var sendername: String
    lateinit var recipientname: String
    lateinit var recipientemail: String
    lateinit var recipientphone: String

    fun asTransaction(): Transaction {
        val transaction = Transaction()
        transaction.recipientPhoneNumber = recipientphone
        transaction.recipientEmail = recipientemail
        transaction.type = type.toDouble().toInt()
        transaction.recipientName = recipientname
        transaction.id = id.toDouble().toInt()
        transaction.senderName = sendername
        transaction.reference = reference
        transaction.credit = credit
        transaction.amount = amount
        transaction.mobile = mobile
        transaction.date = date
        return transaction
    }
}