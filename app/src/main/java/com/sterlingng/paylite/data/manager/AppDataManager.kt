package com.sterlingng.paylite.data.manager

import android.content.Context
import com.sterlingng.paylite.data.model.*
import com.sterlingng.paylite.data.model.realms.UserRealm
import com.sterlingng.paylite.data.model.realms.WalletRealm
import com.sterlingng.paylite.data.repository.local.helper.LocalDataHelper
import com.sterlingng.paylite.data.repository.mock.MockHelper
import com.sterlingng.paylite.data.repository.remote.helpers.PayStackServiceHelper
import com.sterlingng.paylite.data.repository.remote.helpers.RemoteServiceHelper
import com.sterlingng.paylite.di.annotations.ApplicationContext
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rtukpe on 14/03/2018.
 */

@Singleton
class AppDataManager
@Inject
internal constructor(@param:ApplicationContext val context: Context,
                     private val mLocalDataHelper: LocalDataHelper,
                     private val remoteServiceHelper: RemoteServiceHelper,
                     private val mPayStackServiceHelper: PayStackServiceHelper,
                     private val mockHelper: MockHelper) : DataManager {

    override fun deleteAllWallets() = mLocalDataHelper.deleteAllWallets()

    override fun getWallet(): Wallet? = mLocalDataHelper.getWallet()

    override fun saveWallet(wallet: Wallet) = mLocalDataHelper.saveWallet(wallet)

    override fun getWalletRealm(): WalletRealm? = mLocalDataHelper.getWalletRealm()

    override fun resolveBankAccount(accountNumber: String, bankCode: String): Observable<Response> = mPayStackServiceHelper.resolveBankAccount(accountNumber, bankCode)

    override fun resolveCardNumber(bin: String): Observable<Response> = mPayStackServiceHelper.resolveCardNumber(bin)

    override fun getBanks(): Observable<Response> = mPayStackServiceHelper.getBanks()

    override fun signup(data: HashMap<String, Any>): Observable<Response> = remoteServiceHelper.signup(data)

    override fun signin(data: HashMap<String, Any>): Observable<Response> = remoteServiceHelper.signin(data)

    override fun getUser(username: String): Observable<Response> = remoteServiceHelper.getUser(username)

    override fun fundWallet(token: String, data: HashMap<String, Any>): Observable<Response> = remoteServiceHelper.fundWallet(token, data)

    override fun getWallet(token: String, username: String): Observable<Response> = remoteServiceHelper.getWallet(token, username)

    override fun deleteAll() = mLocalDataHelper.deleteAll()

    override fun closeRealm() = mLocalDataHelper.closeRealm()

    override fun deleteAllUsers() = mLocalDataHelper.deleteAllUsers()

    override fun saveUser(user: User) = mLocalDataHelper.saveUser(user)

    override fun getCurrentUser(): User? = mLocalDataHelper.getCurrentUser()

    override fun getUserRealm(): UserRealm? = mLocalDataHelper.getUserRealm()

    override fun mockContacts(): ArrayList<Contact> = mockHelper.mockContacts()

    override fun mockPaymentMethods(): ArrayList<PaymentMethod> = mockHelper.mockPaymentMethods()

    override fun mockCategories(): ArrayList<PaymentCategory> = mockHelper.mockCategories()

    override fun mockTransactions(): ArrayList<Transaction> = mockHelper.mockTransactions()

    override fun mockNotifications(): ArrayList<Notification> = mockHelper.mockNotifications()

    override fun mockCharities(): ArrayList<Charity> = mockHelper.mockCharities()

    override fun mockProjects(): ArrayList<Project> = mockHelper.mockProjects()

    override fun mockDeals(): ArrayList<Deal> = mockHelper.mockDeals()
}