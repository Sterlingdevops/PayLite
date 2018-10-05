package com.sterlingng.paylite.data.repository.local.helper

import com.sterlingng.paylite.data.model.*
import com.sterlingng.paylite.data.model.realms.*
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

    override fun deleteScheduledPayment(payment: ScheduledPayment) {
        getRealm().beginTransaction()
        try {
            getRealm()
                    .where(ScheduledRealm::class.java)
                    .equalTo("reference", payment.reference)
                    .findAll()
                    .deleteAllFromRealm()
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->deleteScheduledPayment")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun getScheduledPayments(): ArrayList<ScheduledPayment> {
        val payments = getRealm().where(ScheduledRealm::class.java).findAll()
        return if (payments.size > 0) payments.asSequence().map { it.asScheduledPayment() }.toList() as ArrayList<ScheduledPayment>
        else ArrayList()
    }

    override fun saveScheduledPayments(payments: ArrayList<ScheduledPayment>) {
        getRealm().beginTransaction()
        try {
            getRealm().copyToRealmOrUpdate(payments.map { it.asScheduledRealm() })
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->saveScheduledPayments")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun getContacts(): ArrayList<PayliteContact> {
        val contacts =
                getRealm().where(ContactRealm::class.java).findAll()
        return if (contacts.size > 0)
            contacts.map {
                it.asPayliteContact()
            } as ArrayList<PayliteContact>
        else ArrayList()
    }

    override fun saveContact(contact: PayliteContact) {
        getRealm().beginTransaction()
        try {
            getRealm().copyToRealmOrUpdate(contact.asContactRealm())
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->saveContact")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun deleteContact(contact: PayliteContact) {
        getRealm().beginTransaction()
        try {
            getRealm()
                    .where(ContactRealm::class.java)
                    .equalTo("id", contact.id)
                    .findAll()
                    .deleteAllFromRealm()
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->deleteContact")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun getCards(): ArrayList<Card> {
        val cards = getRealm().where(CardRealm::class.java).findAll()
        return if (cards.size > 0) cards.map { it.asCard() } as ArrayList<Card>
        else ArrayList()
    }

    override fun saveCard(card: Card) {
        getRealm().beginTransaction()
        try {
            getRealm().copyToRealmOrUpdate(card.asCardRealm())
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->saveCard")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun setCardDefault(card: Card) {
        getRealm().beginTransaction()
        try {
            val cards = getRealm()
                    .where(CardRealm::class.java)
                    .findAll().map { it.asCard() }
            val banks = getRealm()
                    .where(BankRealm::class.java)
                    .findAll().map { it.asBank() }
            banks.forEach { it.default = false }
            cards.forEach { it.default = it.number == card.number }
            getRealm().copyToRealmOrUpdate(cards.map { it.asCardRealm() })
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->setCardDefault")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun deleteCard(cardNumber: String) {
        getRealm().beginTransaction()
        try {
            getRealm()
                    .where(CardRealm::class.java)
                    .equalTo("number", cardNumber)
                    .findAll()
                    .deleteAllFromRealm()
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->deleteCard")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun getBanks(): ArrayList<Bank> {
        val banks = getRealm().where(BankRealm::class.java).findAll()
        return if (banks.size > 0) banks.map { it.asBank() } as ArrayList<Bank>
        else ArrayList()
    }

    override fun saveBank(bank: Bank) {
        getRealm().beginTransaction()
        try {
            getRealm().copyToRealmOrUpdate(bank.asBankRealm())
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->saveBank")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun setBankDefault(bank: Bank) {
        getRealm().beginTransaction()
        try {
            val cards = getRealm()
                    .where(CardRealm::class.java)
                    .findAll().map { it.asCard() }
            val banks = getRealm()
                    .where(BankRealm::class.java)
                    .findAll().map { it.asBank() }
            cards.forEach { it.default = false }
            banks.forEach { it.default = it.accountnumber == bank.accountnumber }
            val items = getRealm().copyToRealmOrUpdate(banks.map { it.asBankRealm() })
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->setBankDefault")
        } finally {
            getRealm().commitTransaction()
        }
    }

    override fun deleteBank(accountNumber: String) {
        getRealm().beginTransaction()
        try {
            getRealm()
                    .where(BankRealm::class.java)
                    .equalTo("accountnumber", accountNumber)
                    .findAll()
                    .deleteAllFromRealm()
        } catch (e: IllegalArgumentException) {
            Log.e(e, "LocalDataHelper->deleteBank")
        } finally {
            getRealm().commitTransaction()
        }
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
        return getRealm()
                .where(UserRealm::class.java)
                .findFirst()
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
        return getRealm()
                .where(PinRealm::class.java)
                .equalTo("phone", phone)
                .findFirst()
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

    private val config: RealmConfiguration = RealmConfiguration.Builder()
            .schemaVersion(13).migration(Migrations()).build()
    private val realm: Realm

    init {
        realm = Realm.getInstance(config)
    }
}