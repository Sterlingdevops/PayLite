package com.sterlingng.paylite.data.manager

import android.content.Context
import com.sterlingng.paylite.data.model.*
import com.sterlingng.paylite.data.model.realms.PinRealm
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

    override fun validateSecurityQuestion(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.validateSecurityQuestion(data, authorization, hash)

    override fun setSecurityQuestion(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.setSecurityQuestion(data, authorization, hash)

    override fun mockQuestions(): ArrayList<Question> = mMockHelper.mockQuestions()

    override fun mockVAS(): ArrayList<VAService> = mMockHelper.mockVAS()

    override fun updateTransactionCategory(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.updateTransactionCategory(data, authorization, hash)

    override fun getUserRelativeTransactions(primaryAcct: String, secondaryAcct: String, authorization: String): Observable<Response> = mRemoteServiceHelper.getUserRelativeTransactions(primaryAcct, secondaryAcct, authorization)

    override fun updateForgotPassword(data: HashMap<String, Any>, hash: String): Observable<Response> = mRemoteServiceHelper.updateForgotPassword(data, hash)

    override fun mockMenuItems(): ArrayList<MenuItem> = mMockHelper.mockMenuItems()

    override fun updateUserDetails(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.updateUserDetails(data, authorization, hash)

    override fun deleteAllScheduledPayments() = mLocalDataHelper.deleteAllScheduledPayments()

    override fun cashOutViaPayCode(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.cashOutViaPayCode(data, authorization, hash)

    override fun cashOutViaBranch(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.cashOutViaBranch(data, authorization, hash)

    override fun deleteScheduledPayment(payment: ScheduledPayment) = mLocalDataHelper.deleteScheduledPayment(payment)

    override fun getScheduledPayments(): ArrayList<ScheduledPayment> = mLocalDataHelper.getScheduledPayments()

    override fun saveScheduledPayments(payments: ArrayList<ScheduledPayment>) = mLocalDataHelper.saveScheduledPayments(payments)

    override fun getSchedulePayments(mobile: String, authorization: String): Observable<Response> = mRemoteServiceHelper.getSchedulePayments(mobile, authorization)

    override fun deleteSchedulePayments(mobile: String, reference: String, authorization: String): Observable<Response> = mRemoteServiceHelper.deleteSchedulePayments(mobile, reference, authorization)

    override fun schedulePayments(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.schedulePayments(data, authorization, hash)

    override fun buyAirtimeFromWallet(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.buyAirtimeFromWallet(data, authorization, hash)

    override fun cashoutToSterlingBankAccount(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.cashoutToSterlingBankAccount(data, authorization, hash)

    override fun cashoutToBankAccount(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.cashoutToBankAccount(data, authorization, hash)

    override fun bankNameEnquiry(accountNumber: String, bankCode: String, authorization: String): Observable<Response> = mRemoteServiceHelper.bankNameEnquiry(accountNumber, bankCode, authorization)

    override fun getContacts(): ArrayList<PayliteContact> = mLocalDataHelper.getContacts()

    override fun saveContact(contact: PayliteContact) = mLocalDataHelper.saveContact(contact)

    override fun deleteContact(contact: PayliteContact) = mLocalDataHelper.deleteContact(contact)

    override fun getBanks(): ArrayList<Bank> = mLocalDataHelper.getBanks()

    override fun getCards(): ArrayList<Card> = mLocalDataHelper.getCards()

    override fun saveCard(card: Card) = mLocalDataHelper.saveCard(card)

    override fun setCardDefault(card: Card) = mLocalDataHelper.setCardDefault(card)

    override fun deleteCard(cardNumber: String) = mLocalDataHelper.deleteCard(cardNumber)

    override fun saveBank(bank: Bank) = mLocalDataHelper.saveBank(bank)

    override fun setBankDefault(bank: Bank) = mLocalDataHelper.setBankDefault(bank)

    override fun deleteBank(accountNumber: String) = mLocalDataHelper.deleteBank(accountNumber)

    override fun getBanks(authorization: String): Observable<Response> = mRemoteServiceHelper.getBanks(authorization)

    override fun splitPayment(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.splitPayment(data, authorization, hash)

    override fun validateOtp(data: HashMap<String, Any>, hash: String): Observable<Response> = mRemoteServiceHelper.validateOtp(data, hash)

    override fun saveTransactions(transactions: ArrayList<Transaction>) = mLocalDataHelper.saveTransactions(transactions)

    override fun deleteAllTransactions() = mLocalDataHelper.deleteAllTransactions()

    override fun getTransaction(id: String): Transaction? = mLocalDataHelper.getTransaction(id)

    override fun saveTransaction(transaction: Transaction) = mLocalDataHelper.saveTransaction(transaction)

    override fun getTransactions(): ArrayList<Transaction> = mLocalDataHelper.getTransactions()

    override fun getPinRealm(phone: String): PinRealm? = mLocalDataHelper.getPinRealm(phone)

    override fun getPin(phone: String): Pin? = mLocalDataHelper.getPin(phone)

    override fun savePin(pin: Pin) = mLocalDataHelper.savePin(pin)

    override fun getWallet(): Wallet? = mLocalDataHelper.getWallet()

    override fun deleteAllWallets() = mLocalDataHelper.deleteAllWallets()

    override fun saveWallet(wallet: Wallet) = mLocalDataHelper.saveWallet(wallet)

    override fun getWalletRealm(): WalletRealm? = mLocalDataHelper.getWalletRealm()

    override fun deleteAll() = mLocalDataHelper.deleteAll()

    override fun closeRealm() = mLocalDataHelper.closeRealm()

    override fun deleteAllUsers() = mLocalDataHelper.deleteAllUsers()

    override fun saveUser(user: User) = mLocalDataHelper.saveUser(user)

    override fun getCurrentUser(): User? = mLocalDataHelper.getCurrentUser()

    override fun getUserRealm(): UserRealm? = mLocalDataHelper.getUserRealm()

    override fun mockPaymentCategories(): ArrayList<PaymentCategory> = mMockHelper.mockPaymentCategories()

    override fun mockNotifications(): ArrayList<Notification> = mMockHelper.mockNotifications()

    override fun signup(data: HashMap<String, Any>, hash: String): Observable<Response> = mRemoteServiceHelper.signup(data, hash)

    override fun sendOtp(data: HashMap<String, Any>, hash: String): Observable<Response> = mRemoteServiceHelper.sendOtp(data, hash)

    override fun resolveCardNumber(bin: String): Observable<Response> = mPayStackServiceHelper.resolveCardNumber(bin)

    override fun getUser(mobile: String, authorization: String): Observable<Response> = mRemoteServiceHelper.getUser(mobile, authorization)

    override fun getWallet(mobile: String, authorization: String): Observable<Response> = mRemoteServiceHelper.getWallet(mobile, authorization)

    override fun sendMoney(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.sendMoney(data, authorization, hash)

    override fun buyAirtime(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.buyAirtime(data, authorization, hash)

    override fun resolveBankAccount(accountNumber: String, bankCode: String): Observable<Response> = mPayStackServiceHelper.resolveBankAccount(accountNumber, bankCode)

    override fun requestPaymentLink(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.requestPaymentLink(data, authorization, hash)

    override fun fundWalletWithCard(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.fundWalletWithCard(data, authorization, hash)

    override fun signIn(username: String, password: String, initializationVector: String, grantType: String): Observable<HashMap<String, Any>> = mRemoteServiceHelper.signIn(username, password, initializationVector, grantType)

    override fun sendOtpForgotPassword(data: HashMap<String, Any>, hash: String): Observable<Response> = mRemoteServiceHelper.sendOtpForgotPassword(data, hash)

    override fun fundWalletWithBankAccount(data: HashMap<String, Any>, authorization: String, hash: String): Observable<Response> = mRemoteServiceHelper.fundWalletWithBankAccount(data, authorization, hash)

    override fun getUserTransactions(mobile: String, toDate: String, fromDate: String, authorization: String): Observable<Response> = mRemoteServiceHelper.getUserTransactions(mobile, toDate, fromDate, authorization)
}