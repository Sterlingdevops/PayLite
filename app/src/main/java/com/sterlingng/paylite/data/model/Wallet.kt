package com.sterlingng.paylite.data.model

import com.google.gson.annotations.SerializedName
import com.sterlingng.paylite.data.model.realms.WalletRealm
import com.sterlingng.paylite.utils.AppUtils.gson

class Wallet {
    @SerializedName("balance")
    var balance: Int = 0

    @SerializedName("_id")
    var walletId: String = ""

    @SerializedName("name")
    var name: String = ""

    fun asWalletRealm(): WalletRealm {
        val walletRealm = WalletRealm()
        walletRealm.name = name
        walletRealm.balance = balance
        walletRealm.walletid = walletId
        return walletRealm
    }

    override fun toString(): String {
        return gson.toJson(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Wallet) other.walletId == walletId else false
    }

    override fun hashCode(): Int {
        var result = balance
        result = 31 * result + walletId.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}