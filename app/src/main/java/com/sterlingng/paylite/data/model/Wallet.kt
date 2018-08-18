package com.sterlingng.paylite.data.model

import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.utils.AppUtils.gson

class Wallet {
    @SerializedName("balance")
    val balance: Int = 0

    @SerializedName("_id")
    val walletId: String = ""

    @SerializedName("name")
    val name: String = ""

    override fun toString(): String {
        return gson.toJson(this)
    }
}