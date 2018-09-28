package com.sterlingng.paylite.data.model

import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.utils.AppUtils.gson

class BankNameEnquiry {

    @SerializedName("SessionID")
    var sessionId: String = ""

    @SerializedName("BeneficiaryName")
    var beneficiaryName: String = ""

    @SerializedName("DestionationBankCode")
    var destinationBankCode: String = ""

    override fun toString(): String {
        return gson.toJson(this)
    }
}