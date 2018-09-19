package com.sterlingng.paylite.data.repository.local.helper

import com.sterlingng.paylite.data.model.Pin
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.data.model.realms.PinRealm
import com.sterlingng.paylite.data.model.realms.TransactionRealm
import com.sterlingng.paylite.data.model.realms.UserRealm
import com.sterlingng.paylite.data.model.realms.WalletRealm
import com.sterlingng.paylite.data.repository.local.Migrations
import com.sterlingng.paylite.data.repository.local.interfaces.LocalDataInterface
import com.sterlingng.paylite.utils.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

/**
 * Created by rtukpe on 22/03/2018.
 */

class LocalDataHelper @Inject
constructor() : LocalDataInterface {

    private val config: RealmConfiguration = RealmConfiguration.Builder()
            .schemaVersion(8).migration(Migrations()).build()
    private val realm: Realm

    init {
        realm = Realm.getInstance(config)
    }

    private fun getRealm(): Realm {
        if (realm.isClosed)
            Realm.getInstance(config)
        return realm
    }

    override fun closeRealm() {
        getRealm().close()
    }

    override fun getCurrentUser(): User? {
        return getUserRealm()?.asUser()
    }

    override fun deleteAllUsers() {
        getRealm().beginTransaction()
        try {
            getRealm().delete(UserRealm::class.java)
        } catch (e: IllegalStateException) {
            Log.e(e, "LocalDataHelper->deleteAllUsers")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun deleteAll() {
        getRealm().beginTransaction()
        try {
            getRealm().deleteAll()
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->deleteAll")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun getUserRealm(): UserRealm? {
        return getRealm().where(UserRealm::class.java).findFirst()
    }

    override fun saveUser(user: User) {
        getRealm().beginTransaction()
        try {
            getRealm().copyToRealmOrUpdate(user.asUserRealm())
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->saveUser")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun deleteAllWallets() {
        getRealm().beginTransaction()
        try {
            getRealm().delete(WalletRealm::class.java)
        } catch (e: IllegalStateException) {
            Log.e(e, "LocalDataHelper->deleteAllWallets")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun getWallet(): Wallet? {
        return getWalletRealm()?.asWallet()
    }

    override fun saveWallet(wallet: Wallet) {
        getRealm().beginTransaction()
        try {
            getRealm().copyToRealmOrUpdate(wallet.asWalletRealm())
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->saveWallet")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun getWalletRealm(): WalletRealm? {
        return getRealm().where(WalletRealm::class.java).findFirst()
    }

    override fun getPinRealm(phone: String): PinRealm? {
        return realm.where(PinRealm::class.java).equalTo("phone", phone) .findFirst()
    }

    override fun getPin(phone: String): Pin? {
        return getPinRealm(phone)?.asPin()
    }

    override fun savePin(pin: Pin) {
        getRealm().beginTransaction()
        try {
            getRealm().copyToRealmOrUpdate(pin.asPinRealm())
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->savePin")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun getTransactions(): ArrayList<Transaction> {
        val transactions =
                getRealm().where(TransactionRealm::class.java).findAll()
        return if (transactions.size > 0)
            transactions.map {
                it.asTransaction()
            } as ArrayList<Transaction>
        else ArrayList()
    }

    override fun deleteAllTransactions() {
        getRealm().beginTransaction()
        try {
            getRealm().delete(TransactionRealm::class.java)
        } catch (e: IllegalStateException) {
            Log.e(e, "LocalDataHelper->deleteAllTransactions")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun saveTransaction(transaction: Transaction) {
        getRealm().beginTransaction()
        try {
            getRealm().copyToRealmOrUpdate(transaction.asTransactionRealm())
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->saveTransaction")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun getTransaction(id: String): Transaction? {
        return getRealm().where(TransactionRealm::class.java)
                .equalTo("id", id)
                .findFirst()?.asTransaction()
    }

    override fun saveTransactions(transactions: ArrayList<Transaction>) {
        getRealm().beginTransaction()
        try {
            getRealm().copyToRealmOrUpdate(transactions.map { it.asTransactionRealm() })
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->saveTransactions")
        } finally {
            getRealm().commitTransaction()
        }
    }
}