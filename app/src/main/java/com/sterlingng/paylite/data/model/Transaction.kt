package com.sterlingng.paylite.data.model

import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.data.model.realms.TransactionRealm
import com.sterlingng.paylite.utils.AppUtils.gson

class Transaction {

    @SerializedName("Refid")
    var id: Int = 0

    @SerializedName("Amount")
    var amount = ""

    @SerializedName("Mobile")
    var mobile: String = ""

    @SerializedName("Transtype")
    var type: Int = 0

    @SerializedName("DebitCreditFlag")
    var credit: String = ""

    @SerializedName("TransDate")
    var date: String = ""

    @SerializedName("SenderName")
    var senderName: String = ""

    @SerializedName("ReceiverName")
    var recipientName: String = ""

    @SerializedName("RecipientEmail")
    var recipientEmail: String = ""

    @SerializedName("PaymentReference")
    var reference: String = ""

    @SerializedName("RecipientPhoneNumber")
    var recipientPhoneNumber: String = ""

    fun asTransactionRealm(): TransactionRealm {
        val transactionRealm = TransactionRealm()
        transactionRealm.recipientphone = recipientPhoneNumber
        transactionRealm.recipientemail = recipientEmail
        transactionRealm.recipientname = recipientName
        transactionRealm.sendername = senderName
        transactionRealm.type = type.toString()
        transactionRealm.reference = reference
        transactionRealm.id = id.toString()
        transactionRealm.amount = amount
        transactionRealm.credit = credit
        transactionRealm.mobile = mobile
        transactionRealm.date = date
        return transactionRealm
    }

    override fun toString(): String {
        return gson.toJson(this)
    }
}