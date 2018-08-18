package com.sterlingng.paylite.data.model.realms

import com.sterlingng.paylite.data.model.Wallet
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class WalletRealm : RealmObject() {
    @PrimaryKey
    lateinit var walletid: String
    lateinit var name: String
    var balance: Int = 0

    fun asWallet(): Wallet {
        val wallet = Wallet()
        wallet.name = name
        wallet.balance = balance
        wallet.walletId = walletid
        return wallet
    }
}