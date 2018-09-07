package com.sterlingng.paylite.data.model

import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.data.model.realms.WalletRealm
import com.sterlingng.paylite.utils.AppUtils.gson

class Wallet {
    @SerializedName("availablebalance")
    var balance: Number = 0

    @SerializedName("customerid")
    var walletId: String = ""

    @SerializedName("nuban")
    var name: String = ""

    fun asWalletRealm(): WalletRealm {
        val walletRealm = WalletRealm()
        walletRealm.name = name
        walletRealm.balance = balance.toInt()
        walletRealm.walletid = walletId
        return walletRealm
    }

    override fun toString(): String {
        return gson.toJson(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Wallet) other.name == name else false
    }

    override fun hashCode(): Int {
        var result = balance.toInt()
        result = 31 * result + walletId.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}