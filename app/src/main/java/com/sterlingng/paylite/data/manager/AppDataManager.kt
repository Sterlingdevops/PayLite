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
                     private val mRemoteServiceHelper: RemoteServiceHelper,
                     private val mPayStackServiceHelper: PayStackServiceHelper,
                     private val mMockHelper: MockHelper) : DataManager {

    override fun sendOtpForgotPassword(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.sendOtpForgotPassword(data)

    override fun getUserTransactions(bvn: String, toDate: String, fromDate: String): Observable<Response> = mRemoteServiceHelper.getUserTransactions(bvn, toDate, fromDate)

    override fun requestPaymentLink(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.requestPaymentLink(data)

    override fun fundWalletWithCard(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.fundWalletWithCard(data)

    override fun validateOtp(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.validateOtp(data)

    override fun buyAirtime(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.buyAirtime(data)

    override fun sendOtp(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.sendOtp(data)

    override fun mockBanks(): ArrayList<Bank> = mMockHelper.mockBanks()

    override fun deleteAllWallets() = mLocalDataHelper.deleteAllWallets()

    override fun getWallet(): Wallet? = mLocalDataHelper.getWallet()

    override fun saveWallet(wallet: Wallet) = mLocalDataHelper.saveWallet(wallet)

    override fun getWalletRealm(): WalletRealm? = mLocalDataHelper.getWalletRealm()

    override fun sendMoney(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.sendMoney(data)

    override fun resolveBankAccount(accountNumber: String, bankCode: String): Observable<Response> = mPayStackServiceHelper.resolveBankAccount(accountNumber, bankCode)

    override fun resolveCardNumber(bin: String): Observable<Response> = mPayStackServiceHelper.resolveCardNumber(bin)

    override fun getBanks(): Observable<Response> = mPayStackServiceHelper.getBanks()

    override fun signup(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.signup(data)

    override fun signin(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.signin(data)

    override fun getUser(bvn: String): Observable<Response> = mRemoteServiceHelper.getUser(bvn)

    override fun fundWalletWithBankAccount(data: HashMap<String, Any>): Observable<Response> = mRemoteServiceHelper.fundWalletWithBankAccount(data)

    override fun getWallet(bvn: String): Observable<Response> = mRemoteServiceHelper.getWallet(bvn)

    override fun deleteAll() = mLocalDataHelper.deleteAll()

    override fun closeRealm() = mLocalDataHelper.closeRealm()

    override fun deleteAllUsers() = mLocalDataHelper.deleteAllUsers()

    override fun saveUser(user: User) = mLocalDataHelper.saveUser(user)

    override fun getCurrentUser(): User? = mLocalDataHelper.getCurrentUser()

    override fun getUserRealm(): UserRealm? = mLocalDataHelper.getUserRealm()

    override fun mockContacts(): ArrayList<PayliteContact> = mMockHelper.mockContacts()

    override fun mockPaymentMethods(): ArrayList<PaymentMethod> = mMockHelper.mockPaymentMethods()

    override fun mockCategories(): ArrayList<PaymentCategory> = mMockHelper.mockCategories()

    override fun mockTransactions(): ArrayList<Transaction> = mMockHelper.mockTransactions()

    override fun mockNotifications(): ArrayList<Notification> = mMockHelper.mockNotifications()

    override fun mockCharities(): ArrayList<Charity> = mMockHelper.mockCharities()

    override fun mockProjects(): ArrayList<Project> = mMockHelper.mockProjects()

    override fun mockDeals(): ArrayList<Deal> = mMockHelper.mockDeals()
}