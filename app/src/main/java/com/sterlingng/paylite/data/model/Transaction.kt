package com.sterlingng.paylite.data.model

import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.utils.AppUtils.gson
import java.util.*

class Transaction {

    var amount = ""

    @SerializedName("Refid")
    var id: String = ""

    @SerializedName("SenderID")
    var sender: String = ""

    @SerializedName("ReceiverID")
    var reciever: String = ""

    var name: String = ""

    var count: String = ""

    @SerializedName("DateAdded")
    var date: Date = Date()

    @SerializedName("PaymentRef")
    var reference: String = ""

    @SerializedName("Transtype")
    val transferType: String = ""

    var type: TransactionType = TransactionType.Credit

    constructor(amount: String, id: String, type: TransactionType, name: String, date: Date) {
        this.id = id
        this.name = name
        this.date = date
        this.type = type
        this.amount = amount
    }

    enum class TransactionType(val i: Int) {
        Credit(0),
        Debit(1);

        override fun toString(): String {
            return if (i == 0) "Credit: $i" else "Debit: $i"
        }
    }

    override fun toString(): String {
        return gson.toJson(this)
    }
}