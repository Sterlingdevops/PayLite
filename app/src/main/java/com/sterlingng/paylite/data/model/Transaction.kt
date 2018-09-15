package com.sterlingng.paylite.data.model

import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.data.model.realms.TransactionRealm
import com.sterlingng.paylite.utils.AppUtils.gson

class Transaction {

    @SerializedName("AmountSent")
    var amount = ""

    @SerializedName("Refid")
    var id: Int = 0

    @SerializedName("SenderID")
    var sender: String = ""

    @SerializedName("ReceiverID")
    var reciever: String = ""

    @SerializedName("ReciepientName")
    var name: String = ""

    @SerializedName("DateAdded")
    var date: String = ""

    @SerializedName("PaymentRef")
    var reference: String = ""

    @SerializedName("Transtype1")
    var type: String = ""

    fun asTransactionRealm(): TransactionRealm {
        val transactionRealm = TransactionRealm()
        transactionRealm.id = id.toString()
        transactionRealm.type = type
        transactionRealm.name = name
        transactionRealm.date = date
        transactionRealm.amount = amount
        transactionRealm.reference = reference
        return transactionRealm
    }

    override fun toString(): String {
        return gson.toJson(this)
    }
}