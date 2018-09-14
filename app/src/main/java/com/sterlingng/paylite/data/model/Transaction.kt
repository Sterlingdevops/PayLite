package com.sterlingng.paylite.data.model

import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.utils.AppUtils.gson

class Transaction {

    @SerializedName("AmountSent")
    var amount = ""

    @SerializedName("Refid")
    var id: String = ""

    @SerializedName("SenderID")
    var sender: String = ""

    @SerializedName("ReceiverID")
    var reciever: String = ""

    @SerializedName("ReciepientName")
    var name: String = ""

    var count: String = ""

    @SerializedName("DateAdded")
    var date: String = ""

    @SerializedName("PaymentRef")
    var reference: String = ""

    @SerializedName("Transtype1")
    var type: String = ""

    override fun toString(): String {
        return gson.toJson(this)
    }
}