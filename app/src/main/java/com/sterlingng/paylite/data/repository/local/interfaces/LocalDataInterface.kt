package com.sterlingng.paylite.data.repository.local.interfaces

import com.sterlingng.paylite.data.model.*
import com.sterlingng.paylite.data.model.realms.PinRealm
import com.sterlingng.paylite.data.model.realms.UserRealm
import com.sterlingng.paylite.data.model.realms.WalletRealm

/**
 * Created by rtukpe on 22/03/2018.
 */

interface LocalDataInterface {

    //logout

    fun deleteAll()
    fun closeRealm()

    //users

    fun deleteAllUsers()
    fun saveUser(user: User)
    fun getCurrentUser(): User?
    fun getUserRealm(): UserRealm?

    // transactions

    fun deleteAllTransactions()
    fun getTransaction(id: String): Transaction?
    fun getTransactions(): ArrayList<Transaction>
    fun saveTransaction(transaction: Transaction)
    fun saveTransactions(transactions: ArrayList<Transaction>)

    // pin

    fun getPinRealm(phone: String): PinRealm?
    fun getPin(phone: String): Pin?
    fun savePin(pin: Pin)

    // wallet

    fun deleteAllWallets()
    fun getWallet(): Wallet?
    fun saveWallet(wallet: Wallet)
    fun getWalletRealm(): WalletRealm?

    // card

    fun getCards(): ArrayList<Card>
    fun saveCard(card: Card)
    fun setCardDefault(card: Card)
    fun deleteCard(cardNumber: String)

    // bank

    fun getBanks(): ArrayList<Bank>
    fun saveBank(bank: Bank)
    fun setBankDefault(bank: Bank)
    fun deleteBank(accountNumber: String)

    // contacts


}
